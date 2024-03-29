/**
 * Project Roles
 * @version 0.9.0
 */

import { Construct } from 'constructs'
import { Role, ServicePrincipal, FederatedPrincipal, ManagedPolicy } from 'aws-cdk-lib/aws-iam'
import { Constants } from './constants'
import { PolicyStatements } from './policy-statements' 

/// -----
/// Roles

export module Roles {

    /// -------
    /// Cognito

    export module Cognito {

        /// --------------------
        /// Unauthenticated Role

        export class UnauthenticatedRole extends Role {

            /// -----------
            /// Constructor

            constructor(scope: Construct, arns: string[], identityPoolRef: any) {
                super(scope, Constants.Cognito.UnauthenticatedRoleId, {
                    description: 'Default role for anonymous users',
                    assumedBy: new FederatedPrincipal(
                        Constants.Cognito.ServiceName, {
                            StringEquals: {
                                'cognito-identity.amazonaws.com:aud': identityPoolRef,
                            },
                            'ForAnyValue:StringLike': {
                                'cognito-identity.amazonaws.com:amr': 'unauthenticated',
                            },
                        },
                        Constants.Cognito.Federated.AuthenticatedAssumeRoleAction),
                    managedPolicies: [
                        new ManagedPolicy(scope, `${Constants.Cognito.UnauthenticatedRoleId}UnauthenticatedPolicy`,
                            {
                                statements: [new PolicyStatements.DynamoDB.BasicCRUDPolicyStatement(arns)]
                            })
                    ]

                });

            }

        }

        /// ------------------
        /// Authenticated Role

        export class AuthenticatedRole extends Role {

            /// -----------
            /// Constructor

            constructor(scope: Construct, arns: string[], identityPoolRef: any) {
                super(scope, Constants.Cognito.AuthenticatedRoleId, {
                    description: 'Default role for authenticated users',
                    assumedBy: new FederatedPrincipal(
                        Constants.Cognito.ServiceName, {
                            StringEquals: {
                                'cognito-identity.amazonaws.com:aud': identityPoolRef,
                            },
                            'ForAnyValue:StringLike': {
                                'cognito-identity.amazonaws.com:amr': 'authenticated',
                            },
                        },
                        Constants.Cognito.Federated.AuthenticatedAssumeRoleAction),
                    managedPolicies: [
                        new ManagedPolicy(scope, `${Constants.Cognito.AuthenticatedRoleId}AuthenticatedPolicy`,
                            {
                                statements: [new PolicyStatements.DynamoDB.BasicCRUDPolicyStatement(arns),
                                             new PolicyStatements.S3.BasicCRUDPolicyStatement(arns)]
                            })
                    ]

                });

            }

        }
        
    }

    /// ---
    /// EC2

    export module EC2 {

        /// --------------
        /// Ingestion Role

        export class IngestionRole extends Role {

            /// -----------
            /// Constructor

            constructor(scope: Construct, arns: string[]) {
                super(scope, Constants.EC2.IngestionRoleID, {
                    assumedBy: new ServicePrincipal(Constants.EC2.ServiceName),
                    managedPolicies: [
                        new ManagedPolicy(scope, `${Constants.EC2.IngestionRoleID}Policy`,
                            {
                                statements: [new PolicyStatements.DynamoDB.BasicCRUDPolicyStatement(arns)]
                            })
                    ]

                });

            }

        }

    }

}
