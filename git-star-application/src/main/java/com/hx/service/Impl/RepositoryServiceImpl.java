package com.hx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hx.common.Result;
import com.hx.common.enums.AppHttpCodeEnum;
import com.hx.entity.DTO.repository.NewRepositoryDTO;
import com.hx.entity.DTO.repository.RepositoryDTO;
import com.hx.entity.DTO.repository.UpdateRepositoryDTO;
import com.hx.entity.PO.Folder;
import com.hx.entity.PO.Repository;
import com.hx.entity.PO.relationship.ProjectRepository;
import com.hx.entity.PO.relationship.RepositoryFolder;
import com.hx.entity.PO.relationship.RepositoryStar;
import com.hx.entity.PO.relationship.RepositoryStats;
import com.hx.mapper.FolderMapper;
import com.hx.mapper.RepositoryMapper;
import com.hx.mapper.relationship.ProjectRepositoryMapper;
import com.hx.mapper.relationship.RepositoryFolderMapper;
import com.hx.mapper.relationship.RepositoryStarMapper;
import com.hx.mapper.relationship.RepositoryStatsMapper;
import com.hx.service.RepositoryService;
import com.hx.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dhx
 * @date 2025/1/8 16:33
 */
@Service
public class RepositoryServiceImpl implements RepositoryService {
    @Autowired
    private RepositoryMapper repositoryMapper;
    @Autowired
    private ProjectRepositoryMapper projectRepositoryMapper;
    @Autowired
    private FolderMapper folderMapper;
    @Autowired
    private RepositoryFolderMapper repositoryFolderMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RepositoryStatsMapper repositoryStatsMapper;
    @Autowired
    private RepositoryStarMapper repositoryStarMapper;
    @Override
    public Result newRepository(NewRepositoryDTO newRepositoryDTO) {
        Repository repository = new Repository(newRepositoryDTO.getName(), newRepositoryDTO.getDescription());
        Folder folder = new Folder(repository.getName());
        if (repositoryMapper.insert(repository) == 1 &&
                projectRepositoryMapper.insert(new ProjectRepository(newRepositoryDTO.getProjectId(), repository.getId())) == 1 &&
                folderMapper.insert(folder) == 1 &&
                repositoryFolderMapper.insert(new RepositoryFolder(repository.getId(), folder.getFolderId())) == 1 &&
                repositoryStatsMapper.insert(new RepositoryStats(repository.getId(), 0, 0, 0)) == 1) {
            return Result.okResult(200, "添加成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result delRepository(Long repositoryId) {
        if (projectRepositoryMapper.delete(new QueryWrapper<ProjectRepository>().eq("repository_id", repositoryId)) == 1
                && repositoryMapper.deleteById(repositoryId) == 1) {
            //TODO 删除文件夹
            return Result.okResult(200, "删除成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result updateRepository(UpdateRepositoryDTO updateRepositoryDTO) {
        if (repositoryMapper.updateById(new Repository(updateRepositoryDTO.getRepositoryId(), updateRepositoryDTO.getName(), updateRepositoryDTO.getDescription())) == 1) {
            return Result.okResult(200, "修改成功");
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result getRepositoryInfo(Long repositoryId,String token) {
        Repository repository = repositoryMapper.selectById(repositoryId);
        RepositoryStats repositoryStats = repositoryStatsMapper.selectOne(new QueryWrapper<RepositoryStats>().eq("repository_id", repositoryId));
        repositoryStats.setView(repositoryStats.getView()+1);
        repositoryStatsMapper.updateById(repositoryStats);
        if (repository != null) {
            if(JwtUtil.validateToken(token)){
                Long userId = JwtUtil.getUserIdFromToken(token);
                RepositoryStar repositoryStar = repositoryStarMapper.selectOne(new QueryWrapper<RepositoryStar>().eq("user_id",userId).eq("repository_id",repositoryId));
                return Result.okResult(new RepositoryDTO(repositoryMapper.selectById(repositoryId), repositoryStats,repositoryStar!=null));
            } else {
                return Result.okResult(new RepositoryDTO(repositoryMapper.selectById(repositoryId), repositoryStats,false));

            }
        }
        return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
    }

    @Override
    public Result getRepositoryList(Page<Repository> page,String token) {
        try{
            Long userId = JwtUtil.getUserIdFromToken(token);
            Page<Repository> repositoryPage = repositoryMapper.selectPage(page, new QueryWrapper<Repository>().eq("repository_visibility", true));
            Page<RepositoryDTO> repositoryDTOPage = getRepositoryDTOPage(repositoryPage,token);
            return Result.okResult(repositoryDTOPage);
        } catch (Exception e){
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result getRepositoryListByProjectId(Page<Repository> page, Long projectId,String token) {
        try {
            List<ProjectRepository> list = projectRepositoryMapper.selectList(new QueryWrapper<ProjectRepository>().eq("project_id", projectId));
            List<Long> repositoryIds = list.stream()
                    .map(ProjectRepository::getRepositoryId)
                    .collect(Collectors.toList());
            if (repositoryIds.isEmpty()) {
                return Result.okResult(new ArrayList<>());
            }
            Page<Repository> repositoryPage = repositoryMapper.selectPage(page, new QueryWrapper<Repository>()
                    .in("repository_id", repositoryIds));
            Page<RepositoryDTO> repositoryDTOPage = getRepositoryDTOPage(repositoryPage,token);
            System.out.println(repositoryDTOPage.getRecords());
            return Result.okResult(repositoryDTOPage);
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @Override
    public Result uploadRepositoryFile(Long repositoryId, String path, MultipartFile file) {
        String url = "http://localhost:8090/file/uploadFile";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("path", String.format("/%s/%s", repositoryId, path.substring(path.indexOf("/"))));
        try {
            ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };
            body.add("file", fileResource);
        } catch (IOException e) {
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        return restTemplate.postForObject(url, requestEntity, Result.class);
    }

    @Override
    public Result getRepositoryFolder(Long repositoryId, String path) {
        String url = String.format("http://localhost:8090/file/getFolder?path=/%s/%s", repositoryId, path);
        return restTemplate.getForObject(url, Result.class);
    }

    @Override
    public Result getRepositoryFile(Long repositoryId, String path) {
        String url = String.format("http://localhost:8090/file/getFile?path=/%s/%s", repositoryId, path);
        return restTemplate.getForObject(url, Result.class);
    }

    @Override
    public Result getRepositoryREADME(Long repositoryId) {
        String url = String.format("http://localhost:8090/file/getFile?path=/%s/README.md", repositoryId);
        return restTemplate.getForObject(url, Result.class);
    }

    @Override
    public Result downloadRepository(Long repositoryId) {
        RepositoryStats repositoryStats = repositoryStatsMapper.selectOne(new QueryWrapper<RepositoryStats>().eq("repository_id",repositoryId));
        repositoryStats.setDownload(repositoryStats.getDownload()+1);
        repositoryStatsMapper.updateById(repositoryStats);
        String url = String.format("http://localhost:8090/file/downloadRepository?path=/%s/", repositoryId);
        return restTemplate.getForObject(url, Result.class);
    }
    @Override
    public Result starRepository(Long repositoryId,String token) {
        try {
            Long userId = JwtUtil.getUserIdFromToken(token);
            RepositoryStats repositoryStats = repositoryStatsMapper.selectOne(new QueryWrapper<RepositoryStats>().eq("repository_id",repositoryId));
            RepositoryStar repositoryStar = repositoryStarMapper.selectOne(new QueryWrapper<RepositoryStar>().eq("user_id",userId).eq("repository_id",repositoryId));
            if(repositoryStar!=null){
                repositoryStarMapper.deleteById(repositoryStar);
                repositoryStats.setStar(repositoryStats.getStar()-1);
                repositoryStatsMapper.updateById(repositoryStats);
                return Result.okResult();
            }
            repositoryStarMapper.insert(new RepositoryStar(userId, repositoryId));
            repositoryStats.setStar(repositoryStats.getStar()+1);
            repositoryStatsMapper.updateById(repositoryStats);
            return Result.okResult();
        } catch (Exception e){
            e.printStackTrace();
            return Result.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }


    private Page<RepositoryDTO> getRepositoryDTOPage(Page<Repository> repositoryPage,String token) {
        List<Repository> repositories = repositoryPage.getRecords();
        List<RepositoryDTO> dtoList = getRepositoryDTOList(repositories,token);
        Page<RepositoryDTO> repositoryDTOPage = new Page<>(
                repositoryPage.getCurrent(),
                repositoryPage.getSize(),
                repositoryPage.getTotal()
        );
        repositoryDTOPage.setRecords(dtoList);
        repositoryDTOPage.setPages(repositoryPage.getPages());
        return repositoryDTOPage;
    }
    private List<RepositoryDTO> getRepositoryDTOList(List<Repository> repositories,String token){
        List<Long> repositoryIds = repositories.stream()
                .map(Repository::getId)
                .collect(Collectors.toList());
        List<RepositoryStats> repositoryStatsList = repositoryStatsMapper.selectList(new QueryWrapper<RepositoryStats>().in("repository_id",repositoryIds));
        Map<Long, RepositoryStats> statsMap = repositoryStatsList.stream()
                .collect(Collectors.toMap(RepositoryStats::getRepositoryId, Function.identity()));
        return repositories.stream()
                .map(repo -> {
                    RepositoryStats stats = statsMap.get(repo.getId());
                    if (stats == null) {
                        stats = new RepositoryStats();
                        stats.setDownload(0);
                        stats.setView(0);
                        stats.setStar(0);
                    }
                    if(JwtUtil.validateToken(token)){
                        Long userId = JwtUtil.getUserIdFromToken(token);
                        RepositoryStar repositoryStar = repositoryStarMapper.selectOne(new QueryWrapper<RepositoryStar>().eq("user_id",userId).eq("repository_id",stats.getRepositoryId()));
                        return new RepositoryDTO(repo, stats,repositoryStar!=null);
                    } else {
                        return new RepositoryDTO(repo, stats,false);
                    }
                })
                .collect(Collectors.toList());
    }
}
