/**
 * Constants
 * @version 0.9.0
 */

import { SubnetType } from 'aws-cdk-lib/aws-ec2'

export module Constants {

    /// ---
    /// App
    
    export const Account    =   '060498623801'      ;
    export const Region     =   'us-west-1'         ;
    export const AppName    =   'DevHour'           ;
    export const DomainName =   'devhour.app'       ;

    /// ------
    /// Stages

    export module Stages {

        /// -----
        /// Alpha

        export module Alpha {

            export const Name = 'Alpha'        ;
            export const Id   = `${Name}Stage` ;            

        }

    }

    /// ---
    /// EC2

    export module EC2 {

        export const IngestionRoleID    =   `${AppName}IngestionRole`   ;
        export const ServiceName        =   `ec2.amazonaws.com`         ;
        export const KeyName            =   'devhour-ingestion'         ;
        
        /// ---------------
        /// InternetGateway

        export module InternetGateway {

            export const Id             = `${AppName}InternetGateway`   ;
            export const StackId        = `${Id}Stack`                  ;    

        }
 
        /// ---
        /// VPC

        export module VPC {
            
            export const Id                 =   `${AppName}VPC`     ;
            export const StackId            =   `${Id}Stack`        ;
            export const CIDR               =   '10.0.0.0/16'       ;
            export const MaxAZs             =   2                   ;
            export const NATGateways        =   0                   ;
            export const EnableDNSHostNames =   true                ;
            export const EnableDNSSupport   =   true                ;

            /// --------------------
            /// Subnet Configuration

            export module SubnetConfiguration {

                export const SubnetName         = `${Constants.EC2.VPC.Id}Subnet`   ;
                export const SubnetCIDRMask     =   24                              ;
                export const Type               =   SubnetType.PUBLIC               ;

            }

        }

    }

    /// -----------
    /// Code Commit

    export module CodeCommit {

        export const Repository             = 'CS472-2022/DevHour'          ;
        export const PrimaryOutputDirectory = 'dev-hour-cdk/cdk.out'        ;

        /// --------
        /// Branches

        export module Branches {

            export const Main   = 'main'  ;

        }

        /// ----------
        /// Connection

        export module Connection {

            export const Arn    = 'arn:aws:codestar-connections:us-west-1:060498623801:connection/6eea9f53-2cab-41e0-afa6-2263aa42e39b';

        }

    }

    /// -------------
    /// Code Pipeline

    export module CodePipeline {

        export const Id         = `${AppName}Pipeline` ;
        export const StackId    = `${Id}Stack`         ;
        export const SelfMutate = true                 ;

        /// ---------
        /// ShellStep

        export module ShellStep {

            export const Id = `${AppName}ShellStep`    ;

        }

    }

    /// --------
    /// Cognito

    export module Cognito {

        export const Id                         =  `${AppName}Cognito`                                                                  ;
        export const StackId                    =  `${Id}Stack`                                                                         ;
        export const ServiceName                =   'cognito-identity.amazonaws.com'                                                    ;
        export const AuthenticatedRoleId        =   `${Id}AuthenticatedRole`                                                            ;
        export const UnauthenticatedRoleId      =   `${Id}UnauthenticatedRole`                                                          ; 
        export const IdentityProviderId         =   `${Id}IdentityProvider`                                                             ;        
        export const IdentityProviderSecretArn  =   "arn:aws:secretsmanager:us-west-1:060498623801:secret:DevHour-Secret-INGIRV"        ;


        /// ---------
        /// Federated

        export module Federated {

            export const AuthenticatedAssumeRoleAction   =   'sts:AssumeRoleWithWebIdentity';

        }

        /// -------------
        /// Identity Pool

        export module IdentityPool {

            export const Id                             =  `${AppName}IdentityPool`     ;
            export const StackId                        =  `${Id}Stack`                 ;
            export const AllowUnauthenticatedIdentities =   false                       ;

        }

        /// ---------
        /// User Pool

        export module UserPool {

            export const Id                 =   `${AppName}UserPool`            ;
            export const StackId            =   `${Id}Stack`                    ;
            export const ClientId           =   `${Id}Client`                   ; 
            export const SelfSignUpEnabled  =   true                            ;
            export const AutoVerifyEmail    =   false                           ;

            /// ---------------
            /// Sign In Aliases

            export module SignInAliases {

                export const EnableUserName =   true                                ;
                export const EnableEmail    =   true                                ;

            }

            /// -------------------
            /// Standard Attributes

            export module StandardAttributes {

                /// ---------
                /// Full Name

                export module FullName {

                    export const Required   =   true                                ;
                    export const Mutable    =   false                               ;

                }

                /// -----
                /// Email

                export module Email {

                    export const Required   =   true                                ;
                    export const Mutable    =   false                               ;

                }

            }

            /// ---------------
            /// Password Policy

            export module PasswordPolicy {

                export const MinimumLength  =   8                                   ;

            }

        }

    }

    /// --------
    /// DynamoDB

    export module DynamoDB {

        export const Id         =  `${AppName}DynamoDB`     ;
        export const StackId    =  `${Id}Stack`             ;

        /// -----------------
        /// Policy Statements

        export module PolicyStatements {

            /// ----------
            /// Basic Read

            export module BasicCRUD {

                export const AllowActions = [   'dynamodb:DescribeTable',
                                                'dynamodb:Scan',
                                                'dynamodb:Query',
                                                'dynamodb:GetItem',
                                                'dynamodb:PutItem',
                                                'dynamodb:UpdateItem',
                                                'dynamodb:DeleteItem'];

            }

        }

    }

    /// --------
    /// Route53
    
    export module Route53 {
        export module Certificate {
            export const Id = `${AppName}Certificate`       ;
            export const StackId = `${Id}Stack`             ;
        }

        export module HostedZone {
            export const Id = `${AppName}HostedZone`        ;
            export const StackId  = `${Id}Stack`            ;

        }
    }

}
