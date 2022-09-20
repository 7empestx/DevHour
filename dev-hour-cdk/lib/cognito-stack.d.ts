import { Stack, Construct } from '@aws-cdk/core';
export interface CognitoProps {
    account: string;
    region: string;
    userPoolID: string;
    identityPoolID: string;
    stackID: string;
    identityProviderID: string;
}
export declare class CognitoStack extends Stack {
    private readonly userPool;
    private readonly Identity;
    private readonly userPoolClient;
    private readonly userPoolIdentityProviderAmazon;
    constructor(scope: Construct, props: CognitoProps);
}
