package com.nowcoder.community.util;

public class RedisKeyUtil
{
    private static final String SPLIT = ":";
    public static final String PREFIX_ENTITY_LIKE = "like:entity";
    public static final String PREFIX_USER_LIKE = "like:user";

    //某个实体的赞
    //like:entity:entityType:entityId -> set(userId)
    //使用set可以获得set所有的个数，还可以得到每个点赞的userid
    public static String getEntityLikeKey(int entityType, int entityId)
    {
        return PREFIX_ENTITY_LIKE + entityType + SPLIT + entityId;
    }

    //某个用户的赞
    //like:user:userId -> int
    public static String getUserLikeKey(int userId)
    {
        return PREFIX_USER_LIKE + SPLIT + userId;
    }
}
