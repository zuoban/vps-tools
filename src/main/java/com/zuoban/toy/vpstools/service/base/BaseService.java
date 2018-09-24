package com.zuoban.toy.vpstools.service.base;

/**
 * @author wangjinqiang
 * @date 2018-09-23
 */

import com.zuoban.toy.vpstools.entity.base.BaseEntity;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


/**
 * 基础Service， 封装一些基础的增删改查方法
 *
 * @author wangjinqiang
 * @date 2018-06-13
 */
public abstract class BaseService<M extends BaseEntity, R extends JpaRepository<M, Long>> {
    @Autowired
    protected R repository;

    /**
     * 保存一个对象
     *
     * @param model
     * @return
     */
    public M save(@NonNull M model) {
        return repository.save(model);
    }

    /**
     * 保存多个对象
     *
     * @param modelList
     * @return
     */
    public <S extends M> List<S> save(Iterable<S> modelList) {
        return repository.saveAll(modelList);
    }

    /**
     * 通过Id查找
     *
     * @param id
     * @return
     */
    public Optional<M> findById(@NonNull Long id) {
        return repository.findById(id);
    }

    /**
     * 通过Id批量查找
     *
     * @param idList
     * @return
     */
    public List<M> findAll(Iterable<Long> idList) {
        return repository.findAllById(idList);
    }

    public List<M> findAll() {
        return repository.findAll();
    }


    /**
     * 通过Id查找是否存在
     *
     * @param id
     * @return
     */
    public boolean exists(@NonNull Long id) {
        return repository.existsById(id);
    }

    /**
     * 通过ID删除
     *
     * @param id
     */
    public void deleteById(@NonNull Long id) {
        repository.deleteById(id);
    }

    /**
     * 通过对象删除
     *
     * @param model
     */
    public void delete(@NonNull M model) {
        repository.delete(model);
    }

    /**
     * 通过 id 批量删除
     *
     * @param ids
     */
    public void delete(Iterable<Long> ids) {
        repository.deleteInBatch(repository.findAllById(ids));
    }
}

