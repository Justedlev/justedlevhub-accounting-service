insert into modes (created_at, modified_at, label)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'OFFLINE'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ONLINE'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SLEEP'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'HIDDEN')
on conflict do nothing;

insert into statuses (created_at, modified_at, label)
values (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'UNCONFIRMED'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTUAL'),
       (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'DEACTIVATED')
on conflict do nothing;