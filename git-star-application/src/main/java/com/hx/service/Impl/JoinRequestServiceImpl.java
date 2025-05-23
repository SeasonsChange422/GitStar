package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.enums.UserProjectAuthEnum;
import com.hx.entity.DTO.JoinRequest.AcceptJoinRequestDTO;
import com.hx.entity.DTO.JoinRequest.AddJoinRequestDTO;
import com.hx.entity.DTO.JoinRequest.RefuseJoinRequestDTO;
import com.hx.entity.PO.JoinRequest;
import com.hx.entity.PO.relationship.UserProject;
import com.hx.mapper.JoinRequestMapper;
import com.hx.mapper.relationship.UserProjectMapper;
import com.hx.service.JoinRequestService;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dhx
 * @date 2025/5/10 16:51
 */
@Service
public class JoinRequestServiceImpl implements JoinRequestService {
    @Autowired
    private JoinRequestMapper joinRequestMapper;
    @Autowired
    private UserProjectMapper userProjectMapper;
    @Override
    public Result addRequest(AddJoinRequestDTO addJoinRequestDTO,String token) {
        try{
            Long userId = JwtUtil.getUserIdFromToken(token);
            UserProject userProject = userProjectMapper.selectOne(new QueryWrapper<UserProject>().eq("user_id",userId).eq("project_id",addJoinRequestDTO.getProjectId()));
            if(userProject!=null){
                return Result.okResult(500,"您已是项目成员");
            }
            JoinRequest joinRequest = joinRequestMapper.selectOne(new QueryWrapper<JoinRequest>().eq("user_id",userId).eq("project_id",addJoinRequestDTO.getProjectId()));
            if(joinRequest!=null){
                return Result.okResult(500,"您已发送过申请");
            }
            joinRequest = new JoinRequest(userId, addJoinRequestDTO.getProjectId());
            joinRequestMapper.insert(joinRequest);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result acceptRequest(AcceptJoinRequestDTO acceptJoinRequestDTO) {
        try{
            JoinRequest joinRequest = joinRequestMapper.selectById(acceptJoinRequestDTO.getId());
            UserProject userProject = new UserProject(joinRequest.getUserId(), joinRequest.getProjectId(), UserProjectAuthEnum.DEVELOPER);
            userProjectMapper.insert(userProject);
            joinRequestMapper.deleteById(joinRequest);
            return Result.okResult();
        }catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getRequestOfProject(Long projectId) {
        try{
            List<JoinRequest> joinRequests = joinRequestMapper.selectList(new QueryWrapper<JoinRequest>().eq("project_id",projectId));
            return Result.okResult(joinRequests);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result refuseRequest(RefuseJoinRequestDTO refuseJoinRequestDTO) {
        try {
            joinRequestMapper.deleteById(refuseJoinRequestDTO.getId());
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
