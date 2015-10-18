# setup type script

```
brew install gradle
brew install npm
npm install -g typescript
```

open Preferences...

search `typescript`

Enable & setup Node interpreter

add Command line options `--module "amd" --target "es5"`

互換性問題が出てきたらその時考える

# Projectについて
- core lamnbda wing 共通のモジュールとAPI
- test coreで作ったアプリのテストサポート用
- tool aws lambda等と連携するためのスクリプトを含むツール群。必要に応じて配下に追加する
- sample 勉強用、実験用、実利用の為のサンプルなどを含むサンプルProject。必要に応じて配下に追加する
- 必要に応じて追加・分離していく

# 規約関係

- 愛称は `ばっさープロジェクト` or `Bassar Project` 。明確に把握出来る場合は `ばっさー` も可
- ライセンスはLGPLあたりになる予定
- 基本的に使う言語は日本語
- 当面はブランチ管理しない。使っても良いけどその場合はgit flowに従う
- Issue管理はgithubを利用する
- CIはまだしない。publicにしたらやるかも
- 残すべきと思った情報はWiki/ソースコード/このファイル/EvernoteのProject bookのどれかに残す
- パッケージ名は仮に `com.makotan.tool.lambda.wing` にする。そのうち変わる予定
- テストは機能を網羅出来るようになるべく書く。ただしサンプルは任意


# awsの設定

build.gradleでの設定関係
~/.aws/credentials に少なくともLambdaFullAccessな権限を持ったcredentialをbassarとして設定する
role/lambda-poweruser にLambdaの実行に必要な権限を持ったroleを指定する
間違えてもrootユーザのcredentialは使用しないこと

sample

```
[bassar]
aws_access_key_id = AWSACCESSKEYID
aws_secret_access_key = AWSSECRETACCESSKEY
```

# プラグインリンク
[AWS Gradle Plugin](https://github.com/classmethod-aws/gradle-aws-plugin)  
[Gradle Typescript Plugin](https://github.com/sothmann/typescript-gradle-plugin)  


# ビルド関係

## sampleのビルド系コマンド
```
gradle :sample/sample1:clean
gradle :sample/sample1:test
gradle :sample/sample1:jar
```

TODO:書き足す

# aws コマンド関係
このコマンドを実行するユーザのcredentialsに必要なのは `AWSLambdaFullAccess` Policy (大事なことなので(ry)  
ある程度のカスタマイズはOKだけどroleが割り当てられないとエラーになる事に注意  
1234567890の所は自分のユーザIDを設定する  

## lambda functionの更新
```
gradle :sample/sample1:jar
aws lambda  --profile bassar --region us-west-2 delete-function --function-name sample1
aws lambda  --profile bassar --region us-west-2 create-function --function-name sample1 --runtime java8 --role arn:aws:iam::1234567890:role/lambda-poweruser --handler com.makotan.libs.lambda.wing.sample.Sample01 --zip-file fileb://sample/sample1/build/libs/sample/sample1-0.0.1-SNAPSHOT.jar --timeout 15 --memory-size 512
```

TODO：書き足す&S3経由のサンプルを追加

## lambda functionの実行
```
aws lambda  --profile bassar --region us-west-2 invoke --function-name sample1 --invocation-type RequestResponse --log-type Tail --payload '{"value": 5}' output.txt
```
