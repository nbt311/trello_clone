package com.example.trellobackend.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InviteRequest {
    private Long invitedUserId;
    private Long workSpaceId;
}
