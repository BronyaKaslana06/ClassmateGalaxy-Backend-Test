package com.se.classmategalaxy.service.impl;

import com.se.classmategalaxy.entity.Member;
import com.se.classmategalaxy.entity.Planet;
import com.se.classmategalaxy.entity.UserFollows;
import com.se.classmategalaxy.mapper.FollowMapper;
import com.se.classmategalaxy.mapper.MemberMapper;
import com.se.classmategalaxy.mapper.PlanetMapper;
import com.se.classmategalaxy.mapper.UserMapper;
import com.se.classmategalaxy.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author wyx20
 * @version 1.0
 * @title RecommendServiceImpl
 * @description
 * @create 2024/1/11 11:19
 */
@Service
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PlanetMapper planetMapper;

    @Autowired
    private FollowMapper followMapper;

    // 计算余弦相似度
    public static double calculateCosineSimilarity(List<Double> vector1, List<Double> vector2) {
        // 计算向量的点积
        double dotProduct = 0;
        for (int i = 0; i < vector1.size(); i++) {
            dotProduct += vector1.get(i) * vector2.get(i);
        }

        // 计算向量的范数
        double normVector1 = calculateNorm(vector1);
        double normVector2 = calculateNorm(vector2);

        // 计算余弦相似度
        if (normVector1 != 0 && normVector2 != 0) {
            return dotProduct / (normVector1 * normVector2);
        } else {
            return 0; // 避免除零错误
        }
    }

    // 计算向量的范数
    private static double calculateNorm(List<Double> vector) {
        double sum = 0;
        for (double element : vector) {
            sum += Math.pow(element, 2);
        }
        return Math.sqrt(sum);
    }

    public HashMap<String,Object> recommendPlanetsForUser(Integer userId) {
        HashMap<String,Object> result=new HashMap<>();
        // 获取目标用户加入的星球
        List<Member> targetUserMemberships = memberMapper.getMembershipsByUserId(userId);
        List<Integer> targetUserPlanetIds = targetUserMemberships.stream().map(Member::getPlanetId).collect(Collectors.toList());

        // 获取其他用户加入的星球
        List<Member> allUserMemberships = memberMapper.getAllMemberships();
        Map<Integer, List<Integer>> userPlanetMap = new HashMap<>();
        for (Member membership : allUserMemberships) {
            userPlanetMap.computeIfAbsent(membership.getUserId(), k -> new ArrayList<>()).add(membership.getPlanetId());
        }

        // 计算目标用户与其他用户的相似度
//        Map<Integer, Double> userSimilarityMap = new HashMap<>();
//        for (Integer otherUserId : userPlanetMap.keySet()) {
//            if (!otherUserId.equals(userId)) {
//                List<Integer> otherUserPlanetIds = userPlanetMap.get(otherUserId);
//                double similarity = calculateCosineSimilarity(
//                        targetUserPlanetIds.stream().map(Double::valueOf).collect(Collectors.toList()),
//                        otherUserPlanetIds.stream().map(Double::valueOf).collect(Collectors.toList())
//                );
//                userSimilarityMap.put(otherUserId, similarity);
//            }
//        }

        double similarityThreshold = 0.1; // 你可以根据需要调整相似度的阈值

        // 根据相似度为用户推荐星球
        List<Planet> recommendedPlanets = new ArrayList<>();
//        for (Integer otherUserId : userSimilarityMap.keySet()) {
//            double similarity = userSimilarityMap.get(otherUserId);
//            if (similarity > similarityThreshold) {
//                List<Member> otherUserMemberships = memberMapper.getMembershipsByUserId(otherUserId);
//                List<Integer> otherUserPlanetIds = otherUserMemberships.stream().map(Member::getPlanetId).collect(Collectors.toList());
//
//                // 推荐其他用户加入的尚未加入的星球
//                List<Integer> notJoinedPlanets = otherUserPlanetIds.stream()
//                        .filter(planetId -> !targetUserPlanetIds.contains(planetId))
//                        .collect(Collectors.toList());
//
//                // 添加到推荐列表
//                for (Integer planetId : notJoinedPlanets) {
//                    recommendedPlanets.add(planetMapper.selectById(planetId));
//                }
//            }
//        }

        //除了协同过滤之外，还要根据标签、名称匹配
        List<String> tags= Arrays.asList(userMapper.selectById(userId).getPersonalTag().split(","));;
        for(String tag : tags){
            recommendedPlanets.addAll(planetMapper.selectLikePlanet(tag));
        }

        //还可以根据关注人加入的推荐
        List<Integer> followedIds=followMapper.getFollowedId(userId);
        for(Integer followedId : followedIds){
            List<Integer> planetIds=memberMapper.getPlanetsByUserId(followedId);
            for(Integer planetId : planetIds){
                recommendedPlanets.add(planetMapper.selectById(planetId));
            }
        }

        Iterator<Planet> iterator = recommendedPlanets.iterator();
        while (iterator.hasNext()) {
            Planet planet = iterator.next();
            planet.setBriefIntroduction(planet.getDescription().substring(0, Math.min(25, planet.getDescription().length())));

            if (memberMapper.checkIfJoined(planet.getPlanetId(), userId) > 0||planetMapper.checkIfOwn(planet.getPlanetId(),userId)>0) {
                iterator.remove(); // 使用迭代器的 remove 方法安全地删除元素
            }
        }

        List<Planet> uniqueRecommendedPlanets = recommendedPlanets.stream()
                .collect(Collectors.toMap(Planet::getPlanetId, Function.identity(), (existing, replacement) -> existing))
                .values()
                .stream()
                .collect(Collectors.toList());

        result.put("planetList",uniqueRecommendedPlanets);
        result.put("status",1);
        return result;
    }
}
