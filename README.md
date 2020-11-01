# VOD-AWS-backend

![Java](https://img.shields.io/static/v1?message=Java&logo=java&labelColor=5c5c5c&color=1182c3&logoColor=white&label=%20)&nbsp;&nbsp;
![Spring](https://img.shields.io/static/v1?message=SpringBoot&logo=Spring&labelColor=green&color=1182c3&logoColor=white&label=%20)&nbsp;&nbsp;
![Python](https://img.shields.io/static/v1?message=Python&logo=Python&labelColor=orange&color=1182c3&logoColor=white&label=%20)&nbsp;&nbsp;
![Postgres](https://img.shields.io/static/v1?message=Postgresql&logo=Postgresql&labelColor=orange&color=1182c3&logoColor=white&label=%20)&nbsp;&nbsp;
![AWS](https://img.shields.io/static/v1?message=AWS&logo=aws&labelColor=orange&color=1182c3&logoColor=white&label=%20)&nbsp;&nbsp;


This project has an experimental backend for VOD system. 
A spring boot project connecting to AWS RDS, S3, SES.


<div>
   <img align="right" alt="arch" width="100%" src="https://github.com/ishandas387/vod-aws-backend/blob/master/arch/VOD.jpg />
</div>
```
vod-aws-backend/lambda/Video convertor_HLS/Lib/site-packages/convert.py
- Is the python file deployed as lamda
- Responsible for creating media convert job and hitting the spring boot rest endpoint for table update.

```

```
P.S springboot is just involved out of habit, just to have consumable endpoints for clients. Everything can be achieved serverless.

```

