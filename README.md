## retrofitdemo
通过自定义CallAdapter、Call、CallBack实现请求前、请求结束的接口回调,主要目的是实现请求前打开请求等待对话框，请求结束关闭对话框。使用如下：
### 定义接口，使用Hcall
```sh
 @GET("getValidatedUsers")
    Hcall<BaseResponse<List<User>>> getValidatedUsers(@Query("userName") String... names);

    @FormUrlEncoded
    @POST("postUser")
    Hcall<User> postUser(@Field("name") String name);

    @POST("postUserName/{name}")
    Hcall<User> postUserName(@Path("name") String name);
```
### 请求网络，回调务必使用HcallBack的子类
```sh
api = ApiUtils.getRetrofit().create(RetrofitApi.class);
api.getValidatedUsers(new String[]{"hai", "huang", "li", "chen"}).enqueue(new BaseCallBack<BaseResponse<List<User>>>(this) {
                    @Override
                    public void onSuccess(Call<BaseResponse<List<User>>> call, Response<BaseResponse<List<User>>> response) {
                        tv_content.setText(response.body().toString());
                    }

                });
api.postUser("hai").enqueue(new BaseCallBack<User>(this) {
                    @Override
                    public void onSuccess(Call<User> call, Response<User> response) {
                        tv_content.setText(response.body().toString());
                    }
                });
```
HcallBack定义如下：
```sh
public interface HcallBack<T> extends Callback<T> {
    void onStart();

    /**
     * it is used when response.isSuccessful() and response.code()==200
     * @param call
     * @param response
     */
    void onSuccess(Call<T> call, Response<T> response);

    void onCompleted();
}
```
HcallBack的子类有
  - BaseCallBack
  - DialogCallback
  
如果你的网络请求使用到网络请求等待对话框，一般使用DialogCallback就够了，ok，就这么简单
