<div style="text-align: center">

# CloudLadder
Empower AI ability

</div>


`CloudLadder`是一个云原生的人工智能编程语言，其核心特点在于能够方便地调用人工智能接口。  
例如，图像分类是一种常用的深度学习技术，在传统的程序设计语言中（例如Python），需要使用深度学习框架或者运行时，同时需要对输入图片做复杂的变换。
而使用CloudLadder则只需要加载图片，并声明需要的操作即可。

### 例
```
// 生成图像的描述文字，该任务称为Image captioning
result = image("test.png") | Image.caption
print(result)
```
