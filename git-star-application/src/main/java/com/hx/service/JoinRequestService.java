package com.hx.service;

import com.hx.common.Result;
import com.hx.entity.DTO.JoinRequest.AcceptJoinRequestDTO;
import com.hx.entity.DTO.JoinRequest.AddJoinRequestDTO;
import com.hx.entity.DTO.JoinRequest.RefuseJoinRequestDTO;

/**
 * @author dhx
 * @date 2025/5/10 16:51
 */
public interface JoinRequestService{
    public Result addRequest(AddJoinRequestDTO addJoinRequestDTO,String token);

    public Result acceptRequest(AcceptJoinRequestDTO acceptJoinRequestDTO);

    public Result getRequestOfProject(Long projectId);

    public Result refuseRequest(RefuseJoinRequestDTO refuseJoinRequestDTO);
}
