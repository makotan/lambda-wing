package com.makotan.libs.lambda.wing.api_gw.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by makotan on 2015/11/01.
 */
@Target( { ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RestMethod {
    enum MethodType {
        Lambda,HttpProxy,Mock,AWSProxy
    }

    enum HttpMethod {
        GET,HEAD,POST,OPTIONS,PUT,DEELTE,TRACE,PATCH,LINK,UNKINK
    }

    /**
     * メソッドの種類。現時点ではLambdaだけ対応
     * @return
     */
    MethodType type() default MethodType.Lambda;

    /**
     * CORSを有効にするかどうか。有効にすると適切と思われる値を自動で埋める
     * @return
     */
    boolean useCORS() default true;

    /**
     * CORSのドメイン設定。デフォルトは全開。コマンドの引数があればそれを優先して利用する
     * @return
     */
    String corsDomain() default "*";

    /**
     * 呼び出しに使うHttpMethod
     * @return
     */
    HttpMethod httpMethod();

    /**
     * Lambdaとして呼び出すFunction名
     * @return
     */
    String functionName();

    /**
     * Lambdaとして呼び出すFunctionのAlias名
     * 無ければグローバル設定を利用する
     * @return
     */
    String aliasName();

    /**
     * IAMを使ってアクセス制限するときに入力のIAMのMapから取得するためのキー
     * キーが無ければ設定されずにIAM制限無しになる
     * @return
     */
    String awsIAMKey();

    /**
     * APIキーを使ってアクセス制御するときに入力のAPIKeyのMapから取得するためのキー
     * キーが無ければ設定されずにAPIKey制限無しになる
     * @return
     */
    String awsAPIKey();

    /**
     * リクエストのマッピング情報。無ければデフォルトを自動で作るように努力する
     * @return
     */
    RequestMapping[] requestMapping();

    /**
     * レスポンスのマッピング情報、無ければデフォルトを自動で作るように努力する
     * @return
     */
    ResponseMapping[] responseMapping();

}
