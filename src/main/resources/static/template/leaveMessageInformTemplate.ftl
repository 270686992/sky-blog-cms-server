<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>博客评论回复通知</title>
        <style type="text/css">
            hr.style-three{
                width:80%;
                margin:20px auto;
                border: 0;
                height: 1px;
                background: #333 linear-gradient(to right, #ccc, #333, #ccc);
            }
        </style>
    </head>

    <body>
        <div style="background-color: #eee; padding-top: 5px;">
            <h2 style="text-align: center; margin: 5px auto; color: #337ab7;">感谢您持续关注博击长空站点!</h2>

            <hr class="style-three">

            <div style="padding: 0;">
                <div style="padding-left: 5px;">
                    <p style="color: #337ab7;">
                        ${nickname}, 你好:
                    </p>

                    <p>
                        您在 <a href="https://www.xilikeli.cn">博击长空</a> WEB 站点中的留言已得到站长回复,内容如下:
                    </p>

                    <p>
                        "${content}"
                    </p>

                    <p>
                        谢谢您的参与,您可以访问: <a href="https://www.xilikeli.cn/messages">https://www.xilikeli.cn/message</a> 查看。
                    </p>
                </div>

                <div style="background-color: #bbb;">
                    <p style="margin-top: 5px;text-align: center;padding: 8px 0 8px 0;">
                        如有意见,建议或文章推荐请通过邮件与我联系
                        <br/>
                        Copyright &copy; 2020-2020 www.xilikeli.cn 版权所有
                    </p>
                </div>
            </div>
        </div>
    </body>
</html>