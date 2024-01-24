package com.justedlev.hub.service.impl;

import com.justedlev.hub.component.EmailNotificationSender;
import com.justedlev.hub.component.account.AccountValidator;
import com.justedlev.hub.configuration.properties.keycloak.KeycloakProperties;
import com.justedlev.hub.model.request.RegistrationRequest;
import com.justedlev.hub.model.response.RegistrationResponse;
import com.justedlev.hub.repository.ContactRepository;
import com.justedlev.hub.repository.entity.Account;
import com.justedlev.hub.repository.entity.Contact;
import com.justedlev.hub.service.RegistrationService;
import com.justedlev.hub.type.ContactType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final EmailNotificationSender emailNotificationSender;
    private final ContactRepository contactRepository;
    private final AccountValidator accountValidator;
    private final ModelMapper mapper;
    private final RealmResource keycloakRealmsResource;
    private final KeycloakProperties properties;

    @Override
    @Transactional
    public RegistrationResponse registration(RegistrationRequest request) {
        accountValidator.validateNickname(request.getNickname());
        var userId = createKeycloakUser(request);
        var account = mapper.map(request, Account.class);
        account.setCreatedBy(userId);
        var contact = mapper.map(request, Contact.class).setType(ContactType.EMAIL.getLabel());
        contact.setAccount(account);
        contactRepository.save(contact);
        emailNotificationSender.sendConfirmation(account, contact);

        return new RegistrationResponse(account.getId());
    }

    private String createKeycloakUser(RegistrationRequest request) {
        var user = createUser(request);
        var response = keycloakRealmsResource.users().create(user);
//        try {
//            log.debug("{}", new ObjectMapper().readValue(((InputStream) response.getEntity()), JsonNode.class));
//        } catch (IOException e) {
//            log.
//        }
        var kcUserId = CreatedResponseUtil.getCreatedId(response);
        var userResource = keycloakRealmsResource.users().get(kcUserId);
        properties.getUserGroups().forEach(userResource::joinGroup);

        return kcUserId;
    }

    private UserRepresentation createUser(RegistrationRequest request) {
        var user = new UserRepresentation();
        user.setCredentials(List.of(createPasswordCredentials(request.getPassword())));
        user.setEnabled(true);
        user.setEmailVerified(false);
        user.setUsername(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());

        return user;
    }

    @NonNull
    private CredentialRepresentation createPasswordCredentials(String password) {
        var passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);

        return passwordCredentials;
    }
}
