package com.chinafight.gongxiangdaoyou.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CacheTags {
    public static List<ProfileTag> get(Integer type,List<String> customTags) {
        List<ProfileTag> tags = new ArrayList<>();
        ProfileTag tags1 = new ProfileTag();
        ProfileTag tags2 = new ProfileTag();
        if(type==2){
            tags1.setTypeName("技能");
            tags1.setTags(Arrays.asList("熟悉当地","会开车","会拍照","会急救","喜欢自驾","会潜水","会攀岩"));
            tags.add(tags1);

            tags2.setTypeName("气质");
            tags2.setTags(Arrays.asList("热情","善良","聪明","担当","阳光","爱笑","洒脱","沉稳","开朗","开放","豁达"));
            tags.add(tags2);
        }else if (type==1){
            tags1.setTypeName("爱好");
            tags1.setTags(Arrays.asList("爱吹牛","会把妹","会编程","会java","会前端","会全栈开发web","会攀岩"));
            tags.add(tags1);

            tags2.setTypeName("气质");
            tags2.setTags(Arrays.asList("热情","善良","聪明","担当","阳光","爱笑","洒脱","沉稳","开朗","开放","豁达"));
            tags.add(tags2);
        }else if(type==3 && customTags.size()>0){
            ProfileTag tag = new ProfileTag();
            tag.setTypeName("自定义");
            tag.setTags(customTags);
            tags.add(tag);
        }
        return tags;
    }

//    //非法标签过滤
//    public static String filterInvalid(String tags) {
//        String[] split = StringUtils.split(tags, ",");
//        List<TagDTO> tagDTOS = get();
//        List<String> tagList = tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
//        String invalid = Arrays.stream(split).filter(t -> StringUtils.isBlank(t) || !tagList.contains(t)).collect(Collectors.joining(","));
//        return invalid;
//    }

}