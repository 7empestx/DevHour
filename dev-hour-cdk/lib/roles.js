"use strict";
/**
 * Project Roles
 * @version 0.9.0
 */
Object.defineProperty(exports, "__esModule", { value: true });
exports.Roles = void 0;
const aws_iam_1 = require("aws-cdk-lib/aws-iam");
const constants_1 = require("./constants");
const policy_statements_1 = require("./policy-statements");
/// -----
/// Roles
var Roles;
(function (Roles) {
    /// -------
    /// Cognito
    let Cognito;
    (function (Cognito) {
        /// --------------------
        /// Unauthenticated Role
        class UnauthenticatedRole extends aws_iam_1.Role {
            /// -----------
            /// Constructor
            constructor(scope, id, arns, identityPoolRef) {
                super(scope, id, {
                    description: 'Default role for anonymous users',
                    assumedBy: new aws_iam_1.FederatedPrincipal(constants_1.Constants.Cognito.ServiceName, {
                        StringEquals: {
                            'cognito-identity.amazonaws.com:aud': identityPoolRef,
                        },
                        'ForAnyValue:StringLike': {
                            'cognito-identity.amazonaws.com:amr': 'unauthenticated',
                        },
                    }, constants_1.Constants.Cognito.Federated.AuthenticatedAssumeRoleAction),
                    managedPolicies: [
                        new aws_iam_1.ManagedPolicy(scope, `${id}Policy`, {
                            statements: [new policy_statements_1.PolicyStatements.DynamoDB.BasicReadPolicyStatement(arns)]
                        })
                    ]
                });
            }
        }
        Cognito.UnauthenticatedRole = UnauthenticatedRole;
        /// ------------------
        /// Authenticated Role
        class AuthenticatedRole extends aws_iam_1.Role {
            /// -----------
            /// Constructor
            constructor(scope, id, arns, identityPoolRef) {
                super(scope, id, {
                    description: 'Default role for authenticated users',
                    assumedBy: new aws_iam_1.FederatedPrincipal(constants_1.Constants.Cognito.ServiceName, {
                        StringEquals: {
                            'cognito-identity.amazonaws.com:aud': identityPoolRef,
                        },
                        'ForAnyValue:StringLike': {
                            'cognito-identity.amazonaws.com:amr': 'authenticated',
                        },
                    }, constants_1.Constants.Cognito.Federated.AuthenticatedAssumeRoleAction),
                    managedPolicies: [
                        new aws_iam_1.ManagedPolicy(scope, `${id}Policy`, {
                            statements: [new policy_statements_1.PolicyStatements.DynamoDB.BasicReadPolicyStatement(arns)]
                        })
                    ]
                });
            }
        }
        Cognito.AuthenticatedRole = AuthenticatedRole;
    })(Cognito = Roles.Cognito || (Roles.Cognito = {}));
})(Roles = exports.Roles || (exports.Roles = {}));
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicm9sZXMuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyJyb2xlcy50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiO0FBQUE7OztHQUdHOzs7QUFHSCxpREFBNkU7QUFDN0UsMkNBQXVDO0FBQ3ZDLDJEQUFzRDtBQUV0RCxTQUFTO0FBQ1QsU0FBUztBQUVULElBQWMsS0FBSyxDQTZFbEI7QUE3RUQsV0FBYyxLQUFLO0lBRWYsV0FBVztJQUNYLFdBQVc7SUFFWCxJQUFjLE9BQU8sQ0FzRXBCO0lBdEVELFdBQWMsT0FBTztRQUVqQix3QkFBd0I7UUFDeEIsd0JBQXdCO1FBRXhCLE1BQWEsbUJBQW9CLFNBQVEsY0FBSTtZQUV6QyxlQUFlO1lBQ2YsZUFBZTtZQUVmLFlBQVksS0FBZ0IsRUFBRSxFQUFVLEVBQUUsSUFBYyxFQUFFLGVBQW9CO2dCQUMxRSxLQUFLLENBQUMsS0FBSyxFQUFFLEVBQUUsRUFBRTtvQkFDYixXQUFXLEVBQUUsa0NBQWtDO29CQUMvQyxTQUFTLEVBQUUsSUFBSSw0QkFBa0IsQ0FDN0IscUJBQVMsQ0FBQyxPQUFPLENBQUMsV0FBVyxFQUFFO3dCQUMzQixZQUFZLEVBQUU7NEJBQ1Ysb0NBQW9DLEVBQUUsZUFBZTt5QkFDeEQ7d0JBQ0Qsd0JBQXdCLEVBQUU7NEJBQ3RCLG9DQUFvQyxFQUFFLGlCQUFpQjt5QkFDMUQ7cUJBQ0osRUFDRCxxQkFBUyxDQUFDLE9BQU8sQ0FBQyxTQUFTLENBQUMsNkJBQTZCLENBQUM7b0JBQzlELGVBQWUsRUFBRTt3QkFDYixJQUFJLHVCQUFhLENBQUMsS0FBSyxFQUFFLEdBQUcsRUFBRSxRQUFRLEVBQ2xDOzRCQUNJLFVBQVUsRUFBRSxDQUFDLElBQUksb0NBQWdCLENBQUMsUUFBUSxDQUFDLHdCQUF3QixDQUFDLElBQUksQ0FBQyxDQUFDO3lCQUM3RSxDQUFDO3FCQUNUO2lCQUVKLENBQUMsQ0FBQztZQUVQLENBQUM7U0FFSjtRQTdCWSwyQkFBbUIsc0JBNkIvQixDQUFBO1FBRUQsc0JBQXNCO1FBQ3RCLHNCQUFzQjtRQUV0QixNQUFhLGlCQUFrQixTQUFRLGNBQUk7WUFFdkMsZUFBZTtZQUNmLGVBQWU7WUFFZixZQUFZLEtBQWdCLEVBQUUsRUFBVSxFQUFFLElBQWMsRUFBRSxlQUFvQjtnQkFDMUUsS0FBSyxDQUFDLEtBQUssRUFBRSxFQUFFLEVBQUU7b0JBQ2IsV0FBVyxFQUFFLHNDQUFzQztvQkFDbkQsU0FBUyxFQUFFLElBQUksNEJBQWtCLENBQzdCLHFCQUFTLENBQUMsT0FBTyxDQUFDLFdBQVcsRUFBRTt3QkFDM0IsWUFBWSxFQUFFOzRCQUNWLG9DQUFvQyxFQUFFLGVBQWU7eUJBQ3hEO3dCQUNELHdCQUF3QixFQUFFOzRCQUN0QixvQ0FBb0MsRUFBRSxlQUFlO3lCQUN4RDtxQkFDSixFQUNELHFCQUFTLENBQUMsT0FBTyxDQUFDLFNBQVMsQ0FBQyw2QkFBNkIsQ0FBQztvQkFDOUQsZUFBZSxFQUFFO3dCQUNiLElBQUksdUJBQWEsQ0FBQyxLQUFLLEVBQUUsR0FBRyxFQUFFLFFBQVEsRUFDbEM7NEJBQ0ksVUFBVSxFQUFFLENBQUMsSUFBSSxvQ0FBZ0IsQ0FBQyxRQUFRLENBQUMsd0JBQXdCLENBQUMsSUFBSSxDQUFDLENBQUM7eUJBQzdFLENBQUM7cUJBQ1Q7aUJBRUosQ0FBQyxDQUFDO1lBRVAsQ0FBQztTQUVKO1FBN0JZLHlCQUFpQixvQkE2QjdCLENBQUE7SUFFTCxDQUFDLEVBdEVhLE9BQU8sR0FBUCxhQUFPLEtBQVAsYUFBTyxRQXNFcEI7QUFFTCxDQUFDLEVBN0VhLEtBQUssR0FBTCxhQUFLLEtBQUwsYUFBSyxRQTZFbEIiLCJzb3VyY2VzQ29udGVudCI6WyIvKipcbiAqIFByb2plY3QgUm9sZXNcbiAqIEB2ZXJzaW9uIDAuOS4wXG4gKi9cblxuaW1wb3J0IHsgQ29uc3RydWN0IH0gZnJvbSAnY29uc3RydWN0cydcbmltcG9ydCB7IFJvbGUsIEZlZGVyYXRlZFByaW5jaXBhbCwgTWFuYWdlZFBvbGljeSB9IGZyb20gJ2F3cy1jZGstbGliL2F3cy1pYW0nXG5pbXBvcnQgeyBDb25zdGFudHMgfSBmcm9tICcuL2NvbnN0YW50cydcbmltcG9ydCB7IFBvbGljeVN0YXRlbWVudHMgfSBmcm9tICcuL3BvbGljeS1zdGF0ZW1lbnRzJyBcblxuLy8vIC0tLS0tXG4vLy8gUm9sZXNcblxuZXhwb3J0IG1vZHVsZSBSb2xlcyB7XG5cbiAgICAvLy8gLS0tLS0tLVxuICAgIC8vLyBDb2duaXRvXG5cbiAgICBleHBvcnQgbW9kdWxlIENvZ25pdG8ge1xuXG4gICAgICAgIC8vLyAtLS0tLS0tLS0tLS0tLS0tLS0tLVxuICAgICAgICAvLy8gVW5hdXRoZW50aWNhdGVkIFJvbGVcblxuICAgICAgICBleHBvcnQgY2xhc3MgVW5hdXRoZW50aWNhdGVkUm9sZSBleHRlbmRzIFJvbGUge1xuXG4gICAgICAgICAgICAvLy8gLS0tLS0tLS0tLS1cbiAgICAgICAgICAgIC8vLyBDb25zdHJ1Y3RvclxuXG4gICAgICAgICAgICBjb25zdHJ1Y3RvcihzY29wZTogQ29uc3RydWN0LCBpZDogc3RyaW5nLCBhcm5zOiBzdHJpbmdbXSwgaWRlbnRpdHlQb29sUmVmOiBhbnkpIHtcbiAgICAgICAgICAgICAgICBzdXBlcihzY29wZSwgaWQsIHtcbiAgICAgICAgICAgICAgICAgICAgZGVzY3JpcHRpb246ICdEZWZhdWx0IHJvbGUgZm9yIGFub255bW91cyB1c2VycycsXG4gICAgICAgICAgICAgICAgICAgIGFzc3VtZWRCeTogbmV3IEZlZGVyYXRlZFByaW5jaXBhbChcbiAgICAgICAgICAgICAgICAgICAgICAgIENvbnN0YW50cy5Db2duaXRvLlNlcnZpY2VOYW1lLCB7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgU3RyaW5nRXF1YWxzOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICdjb2duaXRvLWlkZW50aXR5LmFtYXpvbmF3cy5jb206YXVkJzogaWRlbnRpdHlQb29sUmVmLFxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgJ0ZvckFueVZhbHVlOlN0cmluZ0xpa2UnOiB7XG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICdjb2duaXRvLWlkZW50aXR5LmFtYXpvbmF3cy5jb206YW1yJzogJ3VuYXV0aGVudGljYXRlZCcsXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgICAgICBDb25zdGFudHMuQ29nbml0by5GZWRlcmF0ZWQuQXV0aGVudGljYXRlZEFzc3VtZVJvbGVBY3Rpb24pLFxuICAgICAgICAgICAgICAgICAgICBtYW5hZ2VkUG9saWNpZXM6IFtcbiAgICAgICAgICAgICAgICAgICAgICAgIG5ldyBNYW5hZ2VkUG9saWN5KHNjb3BlLCBgJHtpZH1Qb2xpY3lgLFxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgc3RhdGVtZW50czogW25ldyBQb2xpY3lTdGF0ZW1lbnRzLkR5bmFtb0RCLkJhc2ljUmVhZFBvbGljeVN0YXRlbWVudChhcm5zKV1cbiAgICAgICAgICAgICAgICAgICAgICAgICAgICB9KVxuICAgICAgICAgICAgICAgICAgICBdXG5cbiAgICAgICAgICAgICAgICB9KTtcblxuICAgICAgICAgICAgfVxuXG4gICAgICAgIH1cblxuICAgICAgICAvLy8gLS0tLS0tLS0tLS0tLS0tLS0tXG4gICAgICAgIC8vLyBBdXRoZW50aWNhdGVkIFJvbGVcblxuICAgICAgICBleHBvcnQgY2xhc3MgQXV0aGVudGljYXRlZFJvbGUgZXh0ZW5kcyBSb2xlIHtcblxuICAgICAgICAgICAgLy8vIC0tLS0tLS0tLS0tXG4gICAgICAgICAgICAvLy8gQ29uc3RydWN0b3JcblxuICAgICAgICAgICAgY29uc3RydWN0b3Ioc2NvcGU6IENvbnN0cnVjdCwgaWQ6IHN0cmluZywgYXJuczogc3RyaW5nW10sIGlkZW50aXR5UG9vbFJlZjogYW55KSB7XG4gICAgICAgICAgICAgICAgc3VwZXIoc2NvcGUsIGlkLCB7XG4gICAgICAgICAgICAgICAgICAgIGRlc2NyaXB0aW9uOiAnRGVmYXVsdCByb2xlIGZvciBhdXRoZW50aWNhdGVkIHVzZXJzJyxcbiAgICAgICAgICAgICAgICAgICAgYXNzdW1lZEJ5OiBuZXcgRmVkZXJhdGVkUHJpbmNpcGFsKFxuICAgICAgICAgICAgICAgICAgICAgICAgQ29uc3RhbnRzLkNvZ25pdG8uU2VydmljZU5hbWUsIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICBTdHJpbmdFcXVhbHM6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgJ2NvZ25pdG8taWRlbnRpdHkuYW1hem9uYXdzLmNvbTphdWQnOiBpZGVudGl0eVBvb2xSZWYsXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAnRm9yQW55VmFsdWU6U3RyaW5nTGlrZSc6IHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgJ2NvZ25pdG8taWRlbnRpdHkuYW1hem9uYXdzLmNvbTphbXInOiAnYXV0aGVudGljYXRlZCcsXG4gICAgICAgICAgICAgICAgICAgICAgICAgICAgfSxcbiAgICAgICAgICAgICAgICAgICAgICAgIH0sXG4gICAgICAgICAgICAgICAgICAgICAgICBDb25zdGFudHMuQ29nbml0by5GZWRlcmF0ZWQuQXV0aGVudGljYXRlZEFzc3VtZVJvbGVBY3Rpb24pLFxuICAgICAgICAgICAgICAgICAgICBtYW5hZ2VkUG9saWNpZXM6IFtcbiAgICAgICAgICAgICAgICAgICAgICAgIG5ldyBNYW5hZ2VkUG9saWN5KHNjb3BlLCBgJHtpZH1Qb2xpY3lgLFxuICAgICAgICAgICAgICAgICAgICAgICAgICAgIHtcbiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgc3RhdGVtZW50czogW25ldyBQb2xpY3lTdGF0ZW1lbnRzLkR5bmFtb0RCLkJhc2ljUmVhZFBvbGljeVN0YXRlbWVudChhcm5zKV1cbiAgICAgICAgICAgICAgICAgICAgICAgICAgICB9KVxuICAgICAgICAgICAgICAgICAgICBdXG5cbiAgICAgICAgICAgICAgICB9KTtcblxuICAgICAgICAgICAgfVxuXG4gICAgICAgIH1cblxuICAgIH1cblxufVxuIl19