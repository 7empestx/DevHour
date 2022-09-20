/**
 * Constants
 * @version 0.9.0
 */
import { SubnetType } from 'aws-cdk-lib/aws-ec2';
export declare module Constants {
    const Account = "";
    const Region = "us-west-1";
    const AppName = "DevHour";
    module Stages {
        module Alpha {
            const Name = "Alpha";
            const Id: string;
        }
    }
    module EC2 {
        module VPC {
            const Id: string;
            const StackId: string;
            const CIDR = "10.0.0.0/16";
            const MaxAZs = 2;
            const NATGateways = 0;
            const EnableDNSHostNames = true;
            const EnableDNSSupport = true;
            module SubnetConfiguration {
                const SubnetName: string;
                const SubnetCIDRMask = 24;
                const Type = SubnetType.PRIVATE_ISOLATED;
            }
        }
    }
    module CodePipeline {
        const Id: string;
        const StackId: string;
        const SelfMutate = true;
        module ShellStep {
            const Id: string;
        }
    }
    module Cognito {
        const Id: string;
        const StackId: string;
        const ServiceName = "cognito-identity.amazonaws.com";
        const AuthenticatedRoleId: string;
        const UnauthenticatedRoleId: string;
        const IdentityProviderId: string;
        const IdentityProviderSecretArn = "arn:aws:secretsmanager:<region>:<account-id-number>:secret:<secret-name>-<random-6-characters>";
        module Federated {
            const AuthenticatedAssumeRoleAction = "sts:AssumeRoleWithWebIdentity";
        }
        module IdentityPool {
            const Id: string;
            const StackId: string;
            const AllowUnauthenticatedIdentities = false;
        }
        module UserPool {
            const Id: string;
            const StackId: string;
            const ClientId: string;
            const SelfSignUpEnabled = true;
            module SignInAliases {
                const EnableUserName = true;
                const EnableEmail = true;
            }
            module StandardAttributes {
                module FullName {
                    const Required = true;
                    const Mutable = false;
                }
            }
            module PasswordPolicy {
                const MinimumLength = 8;
            }
        }
    }
    module DynamoDB {
        const Id: string;
        const StackId: string;
        module PolicyStatements {
            module BasicRead {
                const AllowActions: never[];
            }
        }
    }
}
