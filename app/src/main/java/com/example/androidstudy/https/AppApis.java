package com.example.androidstudy.https;

import com.example.androidstudy.model.bean.FeedArticleListData;
import com.example.androidstudy.model.bean.KnowledgeHierarchyData;
import com.example.androidstudy.model.bean.LoginData;
import com.example.androidstudy.model.bean.ProjectClassifyData;
import com.example.androidstudy.model.bean.ProjectListData;
import com.example.androidstudy.model.bean.WxAuthor;
import io.reactivex.Observable;
import java.util.List;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AppApis {

  String HOST = "https://www.wanandroid.com/";

  /**
   * 获取feed文章列表
   *
   * @param num 页数
   * @return feed文章列表数据
   */
  @GET("article/list/{num}/json")
  Observable<BaseResponse<FeedArticleListData>> getFeedArticleList(@Path("num") int num);

  /**
   *
   * @param page page
   * @param k POST search key
   * @return 搜索数据
   */
  @POST("article/query/{page}/json")
  @FormUrlEncoded
  Observable<BaseResponse<FeedArticleListData>> getSearchList(@Path("page") int page,
      @Field("k") String k);

  /**
   *
   * @return 知识体系数据
   */
  @GET("tree/json")
  Observable<BaseResponse<List<KnowledgeHierarchyData>>> getKnowledgeHierarchyData();

  /**
   *
   * @param page page num
   * @param cid second page id
   * @return 知识体系feed文章数据
   */
  @GET("article/list/{page}/json")
  Observable<BaseResponse<FeedArticleListData>> getKnowledgeHierarchyDetailData(
      @Path("page") int page, @Query("cid") int cid);

  /**
   *
   * @return 项目分类数据
   */
  @GET("project/tree/json")
  Observable<BaseResponse<List<ProjectClassifyData>>> getProjectClassifyData();

  /**
   *
   * @param page page num
   * @param cid second page id
   * @return 项目类别数据
   */
  @GET("project/list/{page}/json")
  Observable<BaseResponse<ProjectListData>> getProjectListData(@Path("page") int page,
      @Query("cid") int cid);

  /**
   *
   * @return 公众号列表数据
   */
  @GET("wxarticle/chapters/json")
  Observable<BaseResponse<List<WxAuthor>>> getWxAuthorListData();

  /**
   *
   * @return 获取当前公众号某页的数据
   */
  @GET("wxarticle/list/{id}/{page}/json")
  Observable<BaseResponse<FeedArticleListData>> getWxSumData(@Path("id") int id,
      @Path("page") int page);

  /**
   *
   * @return 指定搜索内容，搜索当前公众号的某页的此类数据
   */
  @GET("wxarticle/list/{id}/{page}/json")
  Observable<BaseResponse<FeedArticleListData>> getWxSearchSumData(@Path("id") int id,
      @Path("page") int page, @Query("k") String k);

  /**
   *
   * @param username user name
   * @param password password
   * @return 登陆数据
   */
  @POST("user/login")
  @FormUrlEncoded
  Observable<BaseResponse<LoginData>> getLoginData(@Field("username") String username,
      @Field("password") String password);

  /**
   *
   * @param username user name
   * @param password password
   * @param repassword re password
   * @return 注册数据
   */
  @POST("user/register")
  @FormUrlEncoded
  Observable<BaseResponse<LoginData>> getRegisterData(@Field("username") String username,
      @Field("password") String password, @Field("repassword") String repassword);

  /**
   *
   * @return 登陆数据
   */
  @GET("user/logout/json")
  Observable<BaseResponse<LoginData>> logout();

  /**
   *
   * @param id article id
   * @return 收藏站内文章数据
   */
  @POST("lg/collect/{id}/json")
  Observable<BaseResponse<FeedArticleListData>> addCollectArticle(@Path("id") int id);

  /**
   *
   * @param title title
   * @param author author
   * @param link link
   * @return 收藏站外文章数据
   */
  @POST("lg/collect/add/json")
  @FormUrlEncoded
  Observable<BaseResponse<FeedArticleListData>> addCollectOutsideArticle(
      @Field("title") String title, @Field("author") String author, @Field("link") String link);


  /**
   *
   * @param page page number
   * @return 收藏列表数据
   */
  @GET("lg/collect/list/{page}/json")
  Observable<BaseResponse<FeedArticleListData>> getCollectList(@Path("page") int page);

  /**
   *
   * @param id article id
   * @param originId origin id
   * @return 取消站内文章数据
   */
  @POST("lg/uncollect/{id}/json")
  @FormUrlEncoded
  Observable<BaseResponse<FeedArticleListData>> cancelCollectPageArticle(@Path("id") int id,
      @Field("originId") int originId);

  /**
   *
   * @param id article id
   * @param originId origin id
   * @return 取消收藏页面站内文章数据
   */
  @POST("lg/uncollect_originId/{id}/json")
  @FormUrlEncoded
  Observable<BaseResponse<FeedArticleListData>> cancelCollectArticle(@Path("id") int id,
      @Field("originId") int originId);

}
