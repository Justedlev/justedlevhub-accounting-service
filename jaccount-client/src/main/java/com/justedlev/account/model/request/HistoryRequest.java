package com.justedlev.account.model.request;

import com.justedlev.model.request.PaginationRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistoryRequest {
    private Set<String> emails;
    private Set<String> usernames;
    private PaginationRequest pageRequest;
}
