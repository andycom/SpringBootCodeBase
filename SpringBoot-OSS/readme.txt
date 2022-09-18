1.dam-sdk 上传图片demo项目

2.sdk：计算图片缩略图，组装上传文件信息所需要的参数。

3.DAM 对接分为两个部分，接入方将图片通过sdk 上传到阿里oss

利用 sdk 返回的图片信息 组装 上传接口需要的信息。

4. CURL：

curl --location --request POST 'http://localhost:9010/oss/file' \
--header 'Content-Type: multipart/form-data' \
--header 'Cookie: JSESSIONID=C7FEAD76DAFADA0ACAE587E9109E7BBE' \
--form 'file=@"/C:/Users/hamish/Pictures/fancv/clipboard1.png"'

