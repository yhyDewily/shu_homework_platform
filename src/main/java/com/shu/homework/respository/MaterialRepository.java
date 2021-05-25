package com.shu.homework.respository;

import com.shu.homework.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {

    // 按上传者获取
    public List<Material> getMaterialsByUploader(String uploader);

    // 获取所有
    public List<Material> findAll();
}
