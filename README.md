# lambda-wing

[![Join the chat at https://gitter.im/makotan/lambda-wing](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/makotan/lambda-wing?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
master ![build status](https://circleci.com/gh/makotan/lambda-wing/tree/master.svg?style=shield&circle-token=5117a9dc61be045880459e10457aa96576c58706)
develop ![build status](https://circleci.com/gh/makotan/lambda-wing/tree/develop.svg?style=shield&circle-token=5117a9dc61be045880459e10457aa96576c58706)
[![Build Status](https://travis-ci.org/makotan/lambda-wing.svg)](https://travis-ci.org/makotan/lambda-wing)


ばっさー  
AWS Lambda javaの便利ツール  

# 詳細
memo.md を見る

# ライセンス
LGPLv3

# 機能
- Lambda javaのdeployとalias設定
- Testのサポート
- 環境に合わせたpropertyファイルの読み替え
- API Gatewayとの連携(鋭意開発中)

# TODO
- AWS API Gatewayと一般的な組み合わせを楽に出来るようにする
- Lambdaで使えるライブラリとの繋ぎのモジュールを作る
- コマンドの実行に必要な引数をjsonに纏めれるようにする(引数で上書き)
- ばっさーの開発がintellijでちょっと辛い(実質CLI)なのをなんとかする
- Logの収集
- Dumpファイルの中を扱いやすく出力出来るようにする(Json)
- その後はそのうち考える
 
# 手順
- lambda-wingをビルドする

```
gradle clean build
```

- coreパッケージを追加して `@LambdaHandler` アノテーションをメソッドに追加する

- 動かす

```
java -jar tool/cli/build/libs/tool/cli-0.0.1-SNAPSHOT.jar --command deployLambda --profile bassar --region us-west-2 --role arn:aws:iam::1234567890:role/lambda-poweruser --basePackage com.makotan.sample --s3Bucket deploy-bucket --s3Key deploy/dev/sample1-0.0.1-SNAPSHOT.jar --path sample/sample1/build/libs/sample/sample1-0.0.1-SNAPSHOT.jar
```

- AWS Consoleでdeploy出来たことを確認する

- `--outputDump`指定で出力したダンプファイルを使って後からaliasを付ける

```
java -jar tool/cli/build/libs/tool/cli-0.0.1-SNAPSHOT.jar  --command assignAlias --inputDump logs/result.dmp --aliasName test --profile bassar --region us-west-2
```

- `--outputDump`指定で出力したダンプファイルを使ってFunctionを削除する  
aliasがついて無いこと`--publishVersion`していることが必須

```
java -jar tool/cli/build/libs/tool/cli-0.0.1-SNAPSHOT.jar  --command dropLambda --inputDump logs/result.dmp --aliasName test --profile bassar --region us-west-2
```

# link

[JAWS](https://github.com/jaws-framework/JAWS) The Serverless Application Framework – Uses bleeding-edge AWS services to redefine how to build massively scalable (and cheap) apps!  
[AWS Lambda Doc](https://aws.amazon.com/jp/documentation/lambda/)  
[Amazon API Gateway Doc](https://aws.amazon.com/jp/documentation/apigateway/)  

