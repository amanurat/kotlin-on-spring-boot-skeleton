package com.example.kotlin.springboot

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentityClient
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityRequest
import com.amazonaws.services.cognitoidentity.model.GetOpenIdTokenForDeveloperIdentityResult
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient
import com.amazonaws.services.simpleemail.model.*
import com.example.kotlin.springboot.security.domain.models.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

class AWS {

    @Component
    @PropertySource("classpath:config/application.yml")
    class  DefaultCredentials : AWSCredentials {
        @Value("\${aws.credentials.accessKey}")
        private lateinit var _accessKey: String
        @Value("\${aws.credentials.secretKey}")
        private lateinit var _secretKey: String

        override fun getAWSAccessKeyId(): String? {
            return _accessKey
        }

        override fun getAWSSecretKey(): String? {
            return _secretKey
        }
    }

    @Component
    @PropertySource("classpath:config/application.yml")
    class CognitoManager {
        @Value("\${aws.cognito.identityPoolId}")
        private lateinit var _identityPoolId: String
        @Value("\${aws.cognito.developerProviderName}")
        private lateinit var _developerProviderName: String

        @Autowired
        private lateinit var _credentials: DefaultCredentials

        private var __client: AmazonCognitoIdentityClient? = null
        private val client: AmazonCognitoIdentityClient
            get(){
                if(null == __client)
                    __client = AmazonCognitoIdentityClient(_credentials)
                return __client!!
            }

        fun getIdentityWithToken(user: User): GetOpenIdTokenForDeveloperIdentityResult {
            val req = GetOpenIdTokenForDeveloperIdentityRequest()
            req.identityPoolId = _identityPoolId
            req.tokenDuration = 60*24
            req.logins = mapOf(_developerProviderName to user.uid)
            return client.getOpenIdTokenForDeveloperIdentity(req)
        }
    }

    @Component
    @PropertySource("classpath:config/application.yml")
    class SESManager {
        @Value("\${aws.ses.from}")
        private lateinit var _from: String

        @Autowired
        private lateinit var _credentials: DefaultCredentials

        private var __client: AmazonSimpleEmailServiceClient? = null
        private val client: AmazonSimpleEmailServiceClient
            get(){
                if(null == __client)
                    __client = AmazonSimpleEmailServiceClient(_credentials)
                return __client!!
            }

        fun sendMessage(toAddresses: List<String>, subject: String, body: String) {
            val message = Message()
                    .withSubject(Content().withData(subject))
                    .withBody(Body().withText(Content().withData(body)))

            val request = SendEmailRequest()
                    .withSource(_from)
                    .withDestination(Destination().withToAddresses(toAddresses))
                    .withMessage(message);

            client.setRegion(Region.getRegion(Regions.US_EAST_1))
            client.sendEmail(request)
        }
    }
}