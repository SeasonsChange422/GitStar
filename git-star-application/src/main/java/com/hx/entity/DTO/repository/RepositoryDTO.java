package com.hx.entity.DTO.repository;

import com.hx.entity.PO.Repository;
import com.hx.entity.PO.relationship.RepositoryStats;
import lombok.Data;

import java.util.Date;

/**
 * @author dhx
 * @date 2025/5/1 21:19
 */
@Data
public class RepositoryDTO extends Repository {
    private int download;
    private int view;
    private int star;
    private boolean starred;

    public RepositoryDTO(int download, int view, int star) {
        this.download = download;
        this.view = view;
        this.star = star;
    }

    public RepositoryDTO(Repository repository, int download, int view, int star) {
        super(repository);
        this.download = download;
        this.view = view;
        this.star = star;
    }

    public RepositoryDTO(Repository repository, RepositoryStats repositoryStats,boolean starred) {
        super(repository);
        this.download = repositoryStats.getDownload();
        this.view = repositoryStats.getView();
        this.star = repositoryStats.getStar();
        this.starred = starred;
    }

}
