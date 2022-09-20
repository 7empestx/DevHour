"use strict";
/**
 * Policy Statements
 * @version 0.9.0
 */
Object.defineProperty(exports, "__esModule", { value: true });
exports.PolicyStatements = void 0;
const aws_iam_1 = require("aws-cdk-lib/aws-iam");
const constants_1 = require("./constants");
var PolicyStatements;
(function (PolicyStatements) {
    let DynamoDB;
    (function (DynamoDB) {
        class BasicReadPolicyStatement extends aws_iam_1.PolicyStatement {
            constructor(resourceArns) {
                super({
                    effect: aws_iam_1.Effect.ALLOW,
                    actions: constants_1.Constants.DynamoDB.PolicyStatements.BasicRead.AllowActions,
                    resources: resourceArns
                });
            }
        }
        DynamoDB.BasicReadPolicyStatement = BasicReadPolicyStatement;
    })(DynamoDB = PolicyStatements.DynamoDB || (PolicyStatements.DynamoDB = {}));
})(PolicyStatements = exports.PolicyStatements || (exports.PolicyStatements = {}));
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicG9saWN5LXN0YXRlbWVudHMuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyJwb2xpY3ktc3RhdGVtZW50cy50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiO0FBQUE7OztHQUdHOzs7QUFFSCxpREFBNkQ7QUFDN0QsMkNBQXVDO0FBRXZDLElBQWMsZ0JBQWdCLENBbUI3QjtBQW5CRCxXQUFjLGdCQUFnQjtJQUUxQixJQUFjLFFBQVEsQ0FlckI7SUFmRCxXQUFjLFFBQVE7UUFFbEIsTUFBYSx3QkFBeUIsU0FBUSx5QkFBZTtZQUV6RCxZQUFZLFlBQXNCO2dCQUM5QixLQUFLLENBQUM7b0JBQ0YsTUFBTSxFQUFNLGdCQUFNLENBQUMsS0FBSztvQkFDeEIsT0FBTyxFQUFLLHFCQUFTLENBQUMsUUFBUSxDQUFDLGdCQUFnQixDQUFDLFNBQVMsQ0FBQyxZQUFZO29CQUN0RSxTQUFTLEVBQUcsWUFBWTtpQkFDM0IsQ0FBQyxDQUFDO1lBRVAsQ0FBQztTQUVKO1FBWFksaUNBQXdCLDJCQVdwQyxDQUFBO0lBRUwsQ0FBQyxFQWZhLFFBQVEsR0FBUix5QkFBUSxLQUFSLHlCQUFRLFFBZXJCO0FBRUwsQ0FBQyxFQW5CYSxnQkFBZ0IsR0FBaEIsd0JBQWdCLEtBQWhCLHdCQUFnQixRQW1CN0IiLCJzb3VyY2VzQ29udGVudCI6WyIvKipcbiAqIFBvbGljeSBTdGF0ZW1lbnRzXG4gKiBAdmVyc2lvbiAwLjkuMFxuICovXG5cbmltcG9ydCB7IEVmZmVjdCwgUG9saWN5U3RhdGVtZW50IH0gZnJvbSAnYXdzLWNkay1saWIvYXdzLWlhbSdcbmltcG9ydCB7IENvbnN0YW50cyB9IGZyb20gJy4vY29uc3RhbnRzJ1xuXG5leHBvcnQgbW9kdWxlIFBvbGljeVN0YXRlbWVudHMge1xuXG4gICAgZXhwb3J0IG1vZHVsZSBEeW5hbW9EQiB7XG5cbiAgICAgICAgZXhwb3J0IGNsYXNzIEJhc2ljUmVhZFBvbGljeVN0YXRlbWVudCBleHRlbmRzIFBvbGljeVN0YXRlbWVudCB7XG5cbiAgICAgICAgICAgIGNvbnN0cnVjdG9yKHJlc291cmNlQXJuczogc3RyaW5nW10pIHtcbiAgICAgICAgICAgICAgICBzdXBlcih7XG4gICAgICAgICAgICAgICAgICAgIGVmZmVjdDogICAgIEVmZmVjdC5BTExPVyxcbiAgICAgICAgICAgICAgICAgICAgYWN0aW9uczogICAgQ29uc3RhbnRzLkR5bmFtb0RCLlBvbGljeVN0YXRlbWVudHMuQmFzaWNSZWFkLkFsbG93QWN0aW9ucyxcbiAgICAgICAgICAgICAgICAgICAgcmVzb3VyY2VzOiAgcmVzb3VyY2VBcm5zXG4gICAgICAgICAgICAgICAgfSk7XG4gICAgICAgICAgICBcbiAgICAgICAgICAgIH1cblxuICAgICAgICB9XG5cbiAgICB9XG5cbn1cbiJdfQ==