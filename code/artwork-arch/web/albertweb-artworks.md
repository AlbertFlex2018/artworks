# albertweb-artworks - arch

## albertweb-artworks:war 网页应用
## 项目文件架构
		
		src
			albertweb.artworks
				entity
					ArtWork	作品实体
					User    用户实体
					Topic	话题实体
					Comment 评论实体
				rest
					ArtWorkRest 作品服务
					UserRest	用户服务
					TopicRest	话题服务
					CommentRest	评论服务
					RestApplication 配置类
				web
		resources
			meta-inf
				persistence.xml
		webapp
			web-inf
				web.xml
			jsf
				works
					index.xhtml 引导页
					dich.xhtml 作品速递页
					search.xhtml 作品查询页
					ranking.xhtml 作品排行榜页
					info.xhtml 作品详情页
					push.xhtml 作品发布页
				mail
					index.xhtml 引导页
					login.xhtml 登录页
					home.xhtml 个人主页
					mail.xhtml 消息邮堆页
					search.xhtml 消息查询页
					mailput.xhtml 消息发布页
					info.xhtml 消息详情页
				topic
					index.xhtml 引导页
					dich.xhtml 话题热点页
					search.xhtml 话题查询页
					info.xhtml 话题想起页
					attach.xhtml 参与话题页
					push.xhtml 发布话题页
			resources
				css css库
				js  javascript库
				img 默认图片库
			index.xhtml 主页

页面功能:
主页入口
作品一角
交流话题
消息邮堆

