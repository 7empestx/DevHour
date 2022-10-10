/**
 * Alpha Stage
 * @version 0.9.0
 */

import { Construct } from 'constructs'
import { Stage } from 'aws-cdk-lib'
import { VpcStack } from './vpc-stack'
import { CognitoStack } from './cognito-stack'
import { Constants } from './constants'
import { DynamoDBStack } from './dynamoDB-stack'
import { AttributeType } from 'aws-cdk-lib/aws-dynamodb'
import { EC2Stack } from './ec2-stack'

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
    private readonly userTestTableStack: DynamoDBStack;
    private readonly restaurantTestTableStack: DynamoDBStack;
    private readonly mealTestTableStack: DynamoDBStack;
    private readonly dietTestTableStack: DynamoDBStack;
    private readonly ingredientTestTableStack: DynamoDBStack;
    private readonly menuTestTableStack: DynamoDBStack;
    private readonly ec2Stack: EC2Stack;

    /// -----------
    /// Constructor

    constructor(scope: Construct, props: AlphaStageProps) {
        super(scope, props.stageId, { env: {
            account:    props.account,
            region:     props.region
        }});

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

        this.userTestTableStack = new DynamoDBStack(this, {
            partitionKey: { name: 'id', type: AttributeType.STRING },
            tableName:    "userTestTableAlpha",
            stackId:      "userTestTableAlphaStack",
            id:           "userTestTableAlphaID",
            accountId:    Constants.Account, 
            region:       Constants.Region,
        });
        this.restaurantTestTableStack = new DynamoDBStack(this, {
            partitionKey: { name: 'id', type: AttributeType.STRING },
            tableName:    "restaurantTestTableAlpha",
            stackId:      "restaurantTestTableAlphaStack",
            id:           "restaurantTestTableAlphaID",
            accountId:    Constants.Account, 
            region:       Constants.Region,
        });
        this.mealTestTableStack = new DynamoDBStack(this, {
            partitionKey: { name: 'id', type: AttributeType.STRING },
            tableName:    "mealTestTableAlpha",
            stackId:      "mealTestTableAlphaStack",
            id:           "mealTestTableAlphaID",
            accountId:    Constants.Account, 
            region:       Constants.Region,
        });
        this.dietTestTableStack = new DynamoDBStack(this, {
            partitionKey: { name: 'id', type: AttributeType.STRING },
            tableName:    "dietTestTableAlpha",
            stackId:      "dietTestTableAlphaStack",
            id:           "dietTestTableAlphaID",
            accountId:    Constants.Account, 
            region:       Constants.Region,
        });
        this.ingredientTestTableStack = new DynamoDBStack(this, {
            partitionKey: { name: 'id', type: AttributeType.STRING },
            tableName:    "ingredientTestTableAlpha",
            stackId:      "ingredientTestTableAlphaStack",
            id:           "ingredientTestTableAlphaID",
            accountId:    Constants.Account, 
            region:       Constants.Region,
        });
        this.menuTestTableStack = new DynamoDBStack(this, {
            partitionKey: { name: 'id', type: AttributeType.STRING },
            tableName:    "menuTestTableAlpha",
            stackId:      "menuTestTableAlphaStack",
            id:           "menuTestTableAlphaID",
            accountId:    Constants.Account, 
            region:       Constants.Region,
        });

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
            resourceArns:                   [this.userTestTableStack.tableArn, this.restaurantTestTableStack.tableArn, this.mealTestTableStack.tableArn, this.dietTestTableStack.tableArn, this.ingredientTestTableStack.tableArn, this.menuTestTableStack.tableArn],
        });

        this.ec2Stack = new EC2Stack(this, {
            account:      props.account,
            instanceName: "ec2Stack",
            id:           "ec2ID",
            stackId:      "ec2StackID",
            accountId:    Constants.Account,
            region:       Constants.Region,
            vpc:          this.vpcStack.vpc,
        });
    }
}
