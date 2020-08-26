package com.core136.service.oa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core136.bean.oa.Shortcut;
import com.core136.mapper.oa.ShortcutMapper;

import tk.mybatis.mapper.entity.Example;

@Service
public class ShortcutService {
@Autowired
private ShortcutMapper shortcutMapper;

public int insertShortcut(Shortcut shortcut)
{
	return shortcutMapper.insert(shortcut);
}

public int deleteShortcut(Shortcut shortcut)
{
	return shortcutMapper.delete(shortcut);
}

public int updateShortuct(Example example,Shortcut shortcut)
{
	return shortcutMapper.updateByExampleSelective(shortcut, example);
}

public Shortcut selectOneShortcut(Shortcut shortcut)
{
	return shortcutMapper.selectOne(shortcut);
}

}
