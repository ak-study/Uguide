package com.chinafight.gongxiangdaoyou.service.discover;

import com.chinafight.gongxiangdaoyou.dto.CommentDTO;
import com.chinafight.gongxiangdaoyou.dto.QuestionDTO;
import com.chinafight.gongxiangdaoyou.eunm.CustomerEnum;
import com.chinafight.gongxiangdaoyou.eunm.QuestionEnum;
import com.chinafight.gongxiangdaoyou.mapper.discover.QuestionMapper;
import com.chinafight.gongxiangdaoyou.mapper.profile.UserMapper;
import com.chinafight.gongxiangdaoyou.mapper.utils.ImgMapper;
import com.chinafight.gongxiangdaoyou.model.ImgModel;
import com.chinafight.gongxiangdaoyou.model.discover.Comment;
import com.chinafight.gongxiangdaoyou.model.discover.Question;
import com.chinafight.gongxiangdaoyou.model.profile.UserModel;
import com.chinafight.gongxiangdaoyou.provider.TCProvider;
import com.chinafight.gongxiangdaoyou.utils.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Service
public class QuestionDTOService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentService commentService;
    @Autowired
    TCProvider tcProvider;
    @Autowired
    ImgMapper imgMapper;
    /**
     * 获取所有人的问题列表
     * 通过question里面的creator去user表找对应的id，返回一个user
     * 然后通过questionDTO将question和User拼接
     * @return 返回一个questionDTO列表，其中的questionDTO包含了user信息和对应的question信息
     */
    public List<QuestionDTO> getQuestionDTOList(){
        ArrayList<QuestionDTO> questionDTOs = new ArrayList<>();
        List<Question> questionList = questionMapper.getQuestionList();
        for (Question question : questionList) {
            QuestionDTO questionDTO = new QuestionDTO();
            List<ImgModel> img = imgMapper.getImg(question.getId().toString(), 2);
            BeanUtils.copyProperties(question,questionDTO);
            Long creator = question.getCreator();
            UserModel userModel=userMapper.findUserById(Math.toIntExact(creator));
            questionDTO.setUserModel(userModel);
            questionDTO.setImgList(img);
            questionDTOs.add(questionDTO);
        }
        return questionDTOs;
    }

    /**
     * 获取当前用户的问题列表，封装成questionDTO列表
     * 返回的map包含该用户的所有问题及该问题的所有评论
     * @return 封装了当前用户问题集的列表
     */
    public HashMap<Object, Object> getQuestionDTOListByPerson(Long userId){
        HashMap<Object, Object> map = new HashMap<>();
        HashMap<Object, Object> questionList = new HashMap<>();
        if(userId==null){
            map.put("status",CustomerEnum.ERROR_NULL_POINT.getMsgMap());
            return map;
        }
        List<Question> personalQuestions = questionMapper.getQuestionByPerson(userId);
        for (Question personalQuestion : personalQuestions) {
            HashMap<Object, Object> questions = new HashMap<>();
            //组装一个问题加上这个问题的所有评论成一个map
            List<Comment> comments = commentService.getCommentsByQuestionId(personalQuestion.getId());
            questions.put("question"+ personalQuestion.getId(), personalQuestion);
            questions.put("comment"+ personalQuestion.getId(), comments);
            questionList.put("question"+ personalQuestion.getId(),questions);
        }
        //将这个map加入到questionList列表
        UserModel user = userMapper.findUserById(Math.toIntExact(userId));
        if(personalQuestions.size()==0 || user==null){
            map.put("status",CustomerEnum.ERROR_NULL_USER.getMsgMap());
            return map;
        }
        map.put("questions",questionList);
        map.put("creator",user);
        map.put("status", CustomerEnum.NORMAL_USER_SELECT.getMsgMap());
        return map;
    }

    public HashMap<Object, Object> getQuestionById(Long questionId){
        if (questionId==null){
            return null;
        }
        Question questionByID = questionMapper.getQuestionByID(questionId);
        if(questionByID ==null){
            return null;
        }
        Long creator = questionByID.getCreator();
        UserModel user = userMapper.findUserById(Math.toIntExact(creator));
        List<Comment> comments = commentService.getCommentsByQuestionId(questionId);
        ArrayList<CommentDTO> commentDTOS = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setUser(userMapper.findUserById(Math.toIntExact(comment.getCommentator())));
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTOS.add(commentDTO);
        }
        HashMap<Object, Object> map = new HashMap<>();
        List<ImgModel> img = imgMapper.getImg(questionByID.getId().toString(), 2);
        questionMapper.incViewCount(questionId);//增加问题的浏览次数
        map.put("status",CustomerEnum.NORMAL_USER_SELECT.getMsgMap());
        map.put("creator",user);
        map.put("question", questionByID);
        map.put("comments", commentDTOS);
        map.put("img",img);
        return map;

    }

    public Object uploadQuestionImg(Long questionId, MultipartFile[] file){
        for (MultipartFile multipartFile : file) {
            File f = null;
            try (InputStream inputStream = multipartFile.getInputStream()) {
                f=new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
                Utils.inputStreamToFile(inputStream, f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert f != null;
            URL url = tcProvider.upLoad(f);
            imgMapper.insertImg(2,url.toString(),questionId.toString());
            Utils.deleteTempFile(f);
        }
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    public Object incQuestionLikes(Long questionId){
        if (questionMapper.getQuestionByID(questionId)==null){
            return QuestionEnum.QUESTION_NOT_EXIT.getMsgMap();
        }
        questionMapper.incQuestionLike(questionId);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

    public Object insertTags(String tags,Long questionId){
        if (questionMapper.getQuestionByID(questionId)==null){
            return QuestionEnum.QUESTION_NOT_EXIT.getMsgMap();
        }
        questionMapper.updateTags(tags,questionId);
        return CustomerEnum.NORMAL_STATUS.getMsgMap();
    }

//
//
//    /**
//     *
//     * @param questionDTO 需要查询的问题
//     * @return 与该问题相关联的问题集合（通过tag查询）
//     */
//    public List<QuestionDTO> getRelatedQuestionList(QuestionDTO questionDTO){
//
//    }
}
