/**
 * Cognito Stack
 * @version 0.9.0
 */
import { Stack } from 'aws-cdk-lib';
import { Construct } from 'constructs';
export interface CognitoStackProps {
    account: string;
    region: string;
    id: string;
    stackId: string;
    userPoolId: string;
    identityPoolId: string;
    identityProviderId: string;
    userPoolClientId: string;
    selfSignUpEnabled: boolean;
    enableAliasUsername: boolean;
    enableAliasEmail: boolean;
    fullnameRequired: boolean;
    fullnameMutable: boolean;
    passwordMinimumLength: number;
    allowUnauthenticatedIdentities: boolean;
    resourceArns: string[];
}
export declare class CognitoStack extends Stack {
    private readonly userPool;
    private readonly identityPool;
    private readonly userPoolClient;
    private readonly userPoolIdentityProviderAmazon;
    constructor(scope: Construct, props: CognitoStackProps);
}
