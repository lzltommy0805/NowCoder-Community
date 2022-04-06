package com.nowcoder.community.util;

public class RedisKeyUtil
{
    private static final String SPLIT = ":";
    public static final String PREFIX_ENTITY_LIKE = "like:entity";
    public static final String PREFIX_USER_LIKE = "like:user";
    public static final String PREFIX_FOLLOWEE = "followee";
    public static final String PREFIX_FOLLOWER = "follower";
    public static final String PREFIX_KAPTCHA = "kaptcha";
    public static final String PREFIX_TICKET = "ticket";
    public static final String PREFIX_USER = "user";
    public static final String PREFIX_UV = "uv";
    public static final String PREFIX_DAU = "dau";
    public static final String PREFIX_POST = "post";



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

    //某个用户关注的实体
    //zset根据时间当做分数可以列出最近的关注
    //followee:userId:entityType -> zset(entityId,now)
    public static String getFolloweeKey(int userId, int entityType)
    {
        return PREFIX_FOLLOWEE + SPLIT + userId +SPLIT + entityType;
    }

    //某个用户拥有的粉丝
    //follower:entityType:entityId ->zset(userId,now)
    public static String getFollowerKey(int entityType, int entityId)
    {
        return PREFIX_FOLLOWER + SPLIT + entityType + SPLIT + entityId;
    }

    //登录验证码
    public static String getKaptchaKey(String owner)
    {
        return PREFIX_KAPTCHA + SPLIT + owner;
    }

    //登录的凭证
    public static String getTicketKey(String ticket)
    {
        return PREFIX_TICKET + SPLIT + ticket;
    }

    //用户
    public static String getUserKey(int userId)
    {
        return PREFIX_USER + SPLIT + userId;
    }

    //单日UV
    public static String getUVKey(String date)
    {
        return PREFIX_UV + SPLIT + date;
    }

    //区间UV
    public static String getUVKey(String startDate, String endDate)
    {
        return PREFIX_UV + SPLIT + startDate + SPLIT + endDate;
    }

    //单日活跃用户DAU
    public static String getDAUKey(String date)
    {
        return PREFIX_DAU + SPLIT + date;
    }

    //区间活跃用户DAU
    public static String getDAUKey(String startDate, String endDate)
    {
        return PREFIX_DAU + SPLIT + startDate + SPLIT + endDate;
    }
    //帖子分数
    public static String getPOstScoreKey()
    {
        return PREFIX_POST + SPLIT + "score";
    }
}
