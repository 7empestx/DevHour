import {UserPool, UserPoolClient, UserPoolIdentityProviderAmazon} from '@aws-cdk/aws-cognito'
import {Stack, Construct} from '@aws-cdk/core' 
import {Constants} from './constants';
import {IdentityPool} from '@aws-cdk/aws-cognito-identitypool'

export interface CognitoProps {
    account        : string,
    region         : string, 
    userPoolID     : string, 
    identityPoolID : string, 
    stackID        : string,
    identityProviderID : string,
}

export class CognitoStack extends Stack {
    private readonly userPool : UserPool
    private readonly Identity : IdentityPool
    private readonly userPoolClient : UserPoolClient
    private readonly userPoolIdentityProviderAmazon : UserPoolIdentityProviderAmazon

    constructor (scope: Construct, props: CognitoProps) {
        super(scope, props.stackID, { env: {
            account: props.account,
            region: props. region
        }});

        this.userPool = new UserPool(this, props.userPoolID, {
            selfSignUpEnabled: true,
            signInAliases: {
                username: true,
                email: true
            },
            standardAttributes: {
                fullname: {
                    required: true,
                    mutable: false
                }
            },
            passwordPolicy: {
                minLength: 8,
            },
        });
        
        this.userPoolIdentityProviderAmazon = new UserPoolIdentityProviderAmazon(this, props.identityProviderID,{});
    }
}

