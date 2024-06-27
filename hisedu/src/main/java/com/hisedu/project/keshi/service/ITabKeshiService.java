package com.hisedu.project.keshi.service;


import com.hisedu.project.keshi.domain.TabKeshi;

import java.util.List;

//科室管理
public interface ITabKeshiService {
    public List<TabKeshi> selectTabKeshiList(TabKeshi tabKeshi);

}
