package com.hx.controller;

import com.hx.aop.annotation.ProjectAuth;
import com.hx.aop.annotation.UserAuth;
import com.hx.common.Result;
import com.hx.enums.CheckObjectEnum;
import com.hx.entity.DTO.JoinRequest.AcceptJoinRequestDTO;
import com.hx.entity.DTO.JoinRequest.AddJoinRequestDTO;
import com.hx.entity.DTO.JoinRequest.RefuseJoinRequestDTO;
import com.hx.service.JoinRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author dhx
 * @date 2025/5/10 16:50
 */
@RestController
public class JoinRequestController {
    @Autowired
    JoinRequestService joinRequestService;

    @UserAuth
    @PostMapping("/api/joinRequest/addRequest")
    public Result addRequest(@RequestBody AddJoinRequestDTO addJoinRequestDTO, @RequestHeader("token")String token){
        return joinRequestService.addRequest(addJoinRequestDTO,token);
    }

    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @PostMapping("/api/joinRequest/acceptRequest")
    public Result acceptRequest(@RequestBody AcceptJoinRequestDTO acceptJoinRequestDTO){
        return joinRequestService.acceptRequest(acceptJoinRequestDTO);
    }

    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @PostMapping("/api/joinRequest/refuseRequest")
    public Result refuseRequest(@RequestBody RefuseJoinRequestDTO refuseJoinRequestDTO){
        return joinRequestService.refuseRequest(refuseJoinRequestDTO);
    }

    @UserAuth
    @ProjectAuth(checkOperability = true,checkObject = CheckObjectEnum.CHECK_PROJECT)
    @GetMapping("/api/joinRequest/getRequestOfProject")
    public Result getRequestOfProject(@RequestParam Long projectId){
        return joinRequestService.getRequestOfProject(projectId);
    }
}
