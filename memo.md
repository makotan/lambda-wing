# Projectについて
- core lamnbda wing 共通のモジュールとAPI
- test coreで作ったアプリのテストサポート用
- tool aws lambda等と連携するためのスクリプトを含むツール群。必要に応じて配下に追加する
- sample 勉強用、実験用、実利用の為のサンプルなどを含むサンプルProject。必要に応じて配下に追加する
- 必要に応じて追加・分離していく

Special thanks [JAWS](https://github.com/jaws-framework/JAWS)  


# 規約関係

- 愛称は `ばっさープロジェクト` or `Bassar Project` 。明確に把握出来る場合は `ばっさー` も可
- 基本的に使う言語は日本語
- ブランチ管理は基本的にgit flowに従う
- Issue管理はgithubを利用する
- MLは用意しない。とりあえずgitter
- 残すべきと思った情報はWiki/ソースコード/このファイル/EvernoteのProject bookのどれかに残す。とりあえずgitterにメモしてから転記も可
- パッケージ名は `org.lambda_wing`
- テストは機能を網羅出来るようになるべく書く。ただしサンプルは任意
- 質問などがあれば、とりあえずgitterへ


# awsの設定

build.gradleでの設定関係
~/.aws/credentials に少なくとも`LambdaFullAccess`と`AmazonAPIGatewayAdministrator`権限を持ったcredentialをbassarとして設定する
role/lambda-poweruser にLambdaの実行に必要な権限を持ったroleを指定する
間違えてもrootユーザのcredentialは使用しないこと

sample

```
[bassar]
aws_access_key_id = AWSACCESSKEYID
aws_secret_access_key = AWSSECRETACCESSKEY
```

## IAM関係の設定

IAMの各種権限をもつユーザもしくはrouteのユーザで`CloudFormation`で`resources-cf.json`を動かす  
`resources-cf.json`はJAWS Frameworkのを少し変えただけのバージョンでもし、JAWS Frameworkを動かしたことがあればそのまま利用可能  
`Outputs`タブにある`IamRoleArnLambda`と`IamRoleArnApiGateway`の`arn:aws:iam`から始まるValueをこの後使用する  
`IamRoleArnLambda`はLambdaのFunctionに設定用  
`IamRoleArnApiGateway`はAPI Gatewayの権限に設定用  
ここ以外の説明では`IamRoleArnLambda`と`IamRoleArnApiGateway`という表記を使用する。ただし実体はそれぞれの`arn:aws:iam`から始まるValueを指定すると言うこと  
  
通常使用しているユーザorグループに`IamRoleArnLambda`と`IamRoleArnApiGateway`のPassRole権限を付与する  


```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "Stmt1445169334000",
            "Effect": "Allow",
            "Action": [
                "iam:PassRole"
            ],
            "Resource": [
                "arn:aws:iam::1234567890:role/IamRoleArnLambda",
                "arn:aws:iam::1234567890:role/IamRoleArnApiGateway"
            ]
        }
    ]
}
```

Lambda-wingではこの作業以外で強力なIAMの権限をもつユーザは必要としない  



# ビルド関係

## sampleのビルド系コマンド
```
gradle :sample/sample1:clean
gradle :sample/sample1:test
gradle :sample/sample1:jar
```

TODO:書き足す


## lambda functionの作成
```
gradle :sample/sample1:jar
aws lambda  --profile bassar --region us-west-2 delete-function --function-name sample1
aws lambda  --profile bassar --region us-west-2 create-function --function-name sample1 --runtime java8 --role IamRoleArnLambda --handler com.makotan.libs.lambda.wing.sample.Sample01 --zip-file fileb://sample/sample1/build/libs/sample/sample1-0.0.1-SNAPSHOT.jar --timeout 15 --memory-size 512
```

## lambda functionの更新
```
gradle :sample/sample1:jar
aws lambda  --profile bassar --region us-west-2 update-function-code --function-name sample1 --zip-file  fileb://sample/sample1/build/libs/sample/sample1-0.0.1-SNAPSHOT.jar
```

## lambda functionの削除
このコマンドを実行するとバージョンも含めて削除される

```
aws lambda  --profile bassar --region us-west-2 delete-function --function-name sample1
```



TODO：書き足す&S3経由のサンプルを追加

## lambda functionの実行
```
aws lambda  --profile bassar --region us-west-2 invoke --function-name sample1 --invocation-type RequestResponse  --payload '{"value": 5}' output.txt
```

バージョン指定するときの実行方法  
PRODとか数値のバージョン番号とか任意のが使える  

```
aws lambda  --profile bassar --region us-west-2 invoke --function-name sample1 --invocation-type RequestResponse --qualifier PROD --payload '{"value": 5}' output.txt
```


# Lambda Function 自動登録機能
LambdaのFunctionに登録したいメソッド(=Handler)にLambdaHandlerアノテーションを追加する  
アノテーションにvalueを設定するとfunction名はそれになる。valueが無いときはメソッドの名前をfunction名として選択するので通常はセットする  
メモリーやタイムアウトのconfig関係もアノテーションにセットする  
リージョンなどの情報は引数として渡す  

```
java -jar tool/cli/build/libs/tool/cli-0.0.1-SNAPSHOT.jar --command deployLambda --profile bassar --region us-west-2 --role IamRoleArnLambda --basePackage com.makotan.sample --aliasName dev --s3Bucket deploy-bucket --s3Key deploy/dev/sample1-0.0.1-SNAPSHOT.jar --path sample/sample1/build/libs/sample/sample1-0.0.1-SNAPSHOT.jar
```



# setup type script()

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

