package club.huangliang.blog_smart.service.impl;

import club.huangliang.blog_smart.NotFoundException;
import club.huangliang.blog_smart.dao.TypeRepository;
import club.huangliang.blog_smart.po.Type;
import club.huangliang.blog_smart.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;
    @Transactional//这是开启注解的标签
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findOne(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort =new Sort(Sort.Direction.DESC,"blogs.size");//排序》》参数一是排序的方式，而是根据的什么排序
        Pageable pageable =new PageRequest(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t =typeRepository.findOne(id);
        if (t==null){
            throw  new NotFoundException("不存在该类");
        }
        BeanUtils.copyProperties(type,t);//这个BeanUtils的方法是将type里面的内容复制给t
        return typeRepository.save(t);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.delete(id);
    }
}
