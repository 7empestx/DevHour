/**
 * Alpha Stage
 * @version 0.9.0
 */

import { Construct } from 'constructs'
import { Stage } from 'aws-cdk-lib'
import { VpcStack } from './vpc-stack'
import { CognitoStack } from './cognito-stack'
import { Constants } from './constants'

/// ----------------
/// AlphaStage Props

export interface AlphaStageProps {
    account:   string,
    region:    string,
    stageId:   string,
    stageName: string
}

/// -------------------------
/// AlphaStage Implementation

export class AlphaStage extends Stage {

    /// ---------------
    /// Private Members

    private readonly cognitoStack: CognitoStack;
    private readonly vpcStack: VpcStack;

    /// -----------
    /// Constructor

    constructor(scope: Construct, props: AlphaStageProps) {
        super(scope, props.stageId, { env: {
            account:    props.account,
            region:     props.region
        }});

        this.cognitoStack = new CognitoStack(this, {
            account:                        props.account,
            region:                         props.region, 
            id:                             Constants.Cognito.Id,
            stackId:                        Constants.Cognito.StackId,
            userPoolId:                     Constants.Cognito.UserPool.Id,
            identityPoolId:                 Constants.Cognito.IdentityPool.Id,
            identityProviderId:             Constants.Cognito.IdentityProviderId,
            userPoolClientId:               Constants.Cognito.UserPool.ClientId,
            selfSignUpEnabled:              Constants.Cognito.UserPool.SelfSignUpEnabled,
            enableAliasUsername:            Constants.Cognito.UserPool.SignInAliases.EnableUserName,
            enableAliasEmail:               Constants.Cognito.UserPool.SignInAliases.EnableEmail,
            fullnameRequired:               Constants.Cognito.UserPool.StandardAttributes.FullName.Required,
            fullnameMutable:                Constants.Cognito.UserPool.StandardAttributes.FullName.Mutable,
            passwordMinimumLength:          Constants.Cognito.UserPool.PasswordPolicy.MinimumLength,
            allowUnauthenticatedIdentities: Constants.Cognito.IdentityPool.AllowUnauthenticatedIdentities,
            resourceArns:                   []
        });

        this.vpcStack = new VpcStack(this, {
            account:                props.account,
            region:                 props.region,
            id:                     Constants.EC2.VPC.Id,
            stackId:                Constants.EC2.VPC.StackId,
            cidr:                   Constants.EC2.VPC.CIDR,
            maxAzs:                 Constants.EC2.VPC.MaxAZs,
            natGateways:            Constants.EC2.VPC.NATGateways,
            enableDnsHostnames:     Constants.EC2.VPC.EnableDNSHostNames,
            enableDnsSupport:       Constants.EC2.VPC.EnableDNSSupport,
            subnetConfiguration:    [
                {
                    cidrMask:       Constants.EC2.VPC.SubnetConfiguration.SubnetCIDRMask,
                    name:           Constants.EC2.VPC.SubnetConfiguration.SubnetName,
                    subnetType:     Constants.EC2.VPC.SubnetConfiguration.Type
                }
            ]
        });

    }

}
