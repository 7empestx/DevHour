import { CfnIdentityPool, UserPool, UserPoolClient, UserPoolIdentityProviderAmazon } from 'aws-cdk-lib/aws-cognito'
import { Stack } from 'aws-cdk-lib'
import { Construct } from 'constructs'
import { Role } from 'aws-cdk-lib/aws-iam';
import { Constants } from './constants';

export interface CognitoProps {
    account        : string,
    region         : string, 
    userPoolID     : string, 
    identityPoolID : string, 
    stackID        : string,
    identityProviderID : string,
    userPoolClientID : string,
    userPoolClientSecret : string,
    authenticatedRole: Role,
    unauthenticatedRole: Role,
}

export class CognitoStack extends Stack {
    private readonly userPool : UserPool
    private readonly identityPool : CfnIdentityPool
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
        
        this.userPoolClient = new UserPoolClient(this, props.userPoolClientID, {
            userPool: this.userPool
        })

        this.userPoolIdentityProviderAmazon = new UserPoolIdentityProviderAmazon(this, props.identityProviderID, {
            userPool: this.userPool,
            clientId: props.userPoolClientID,
            clientSecret: props.userPoolClientSecret
        });
        this.identityPool = new CfnIdentityPool(this, props.identityPoolID, {
		allowUnauthenticatedIdentities: false
	});
    }
}

