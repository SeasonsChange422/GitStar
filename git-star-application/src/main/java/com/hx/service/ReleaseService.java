package com.hx.service;

import com.hx.common.Result;
import com.hx.entity.DTO.Release.DelReleaseDTO;
import com.hx.entity.DTO.Release.NewReleaseDTO;
import com.hx.entity.DTO.Release.UpdateReleaseDTO;

/**
 * @author dhx
 * @date 2025/5/9 19:57
 */
public interface ReleaseService {
    public Result newRelease(NewReleaseDTO newReleaseDTO);

    public Result delRelease(DelReleaseDTO delReleaseDTO);

    public Result updateRelease(UpdateReleaseDTO updateReleaseDTO);

    public Result getReleaseOfProject(Long projectId);

    public Result getReleaseInfo(Long releaseId);
}
