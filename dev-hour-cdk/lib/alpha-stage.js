"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.AlphaStage = void 0;
const aws_cdk_lib_1 = require("aws-cdk-lib");
/// -------------------------
/// AlphaStage Implementation
class AlphaStage extends aws_cdk_lib_1.Stage {
    /// ---------------
    /// Private Members
    /// -----------
    /// Constructor
    constructor(scope, props) {
        super(scope, props.stageId, { env: {
                account: props.account,
                region: props.region
            } });
    }
}
exports.AlphaStage = AlphaStage;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYWxwaGEtc3RhZ2UuanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyJhbHBoYS1zdGFnZS50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7QUFLQSw2Q0FBbUM7QUFhbkMsNkJBQTZCO0FBQzdCLDZCQUE2QjtBQUU3QixNQUFhLFVBQVcsU0FBUSxtQkFBSztJQUVqQyxtQkFBbUI7SUFDbkIsbUJBQW1CO0lBRW5CLGVBQWU7SUFDZixlQUFlO0lBRWYsWUFBWSxLQUFnQixFQUFFLEtBQXNCO1FBQ2hELEtBQUssQ0FBQyxLQUFLLEVBQUUsS0FBSyxDQUFDLE9BQU8sRUFBRSxFQUFFLEdBQUcsRUFBRTtnQkFDL0IsT0FBTyxFQUFLLEtBQUssQ0FBQyxPQUFPO2dCQUN6QixNQUFNLEVBQU0sS0FBSyxDQUFDLE1BQU07YUFDM0IsRUFBQyxDQUFDLENBQUM7SUFFUixDQUFDO0NBRUo7QUFoQkQsZ0NBZ0JDIiwic291cmNlc0NvbnRlbnQiOlsiLyoqXG4gKiBBbHBoYSBTdGFnZS4gRGVmaW5lcyB0aGUgcmVzb3VyY2VzIGZvciB0aGUgQWxwaGEgU3RhZ2VcbiAqIEB2ZXJzaW9uIDAuMS4wXG4gKi9cbmltcG9ydCB7IENvbnN0cnVjdCB9IGZyb20gJ2NvbnN0cnVjdHMnXG5pbXBvcnQgeyBTdGFnZSB9IGZyb20gJ2F3cy1jZGstbGliJ1xuaW1wb3J0IHsgQ29uc3RhbnRzIH0gZnJvbSAnLi9jb25zdGFudHMnXG5cbi8vLyAtLS0tLS0tLS0tLS0tLS0tXG4vLy8gQWxwaGFTdGFnZSBQcm9wc1xuXG5leHBvcnQgaW50ZXJmYWNlIEFscGhhU3RhZ2VQcm9wcyB7XG4gICAgYWNjb3VudDogICBzdHJpbmcsXG4gICAgcmVnaW9uOiAgICBzdHJpbmcsXG4gICAgc3RhZ2VJZDogICBzdHJpbmcsXG4gICAgc3RhZ2VOYW1lOiBzdHJpbmdcbn1cblxuLy8vIC0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS1cbi8vLyBBbHBoYVN0YWdlIEltcGxlbWVudGF0aW9uXG5cbmV4cG9ydCBjbGFzcyBBbHBoYVN0YWdlIGV4dGVuZHMgU3RhZ2Uge1xuXG4gICAgLy8vIC0tLS0tLS0tLS0tLS0tLVxuICAgIC8vLyBQcml2YXRlIE1lbWJlcnNcblxuICAgIC8vLyAtLS0tLS0tLS0tLVxuICAgIC8vLyBDb25zdHJ1Y3RvclxuXG4gICAgY29uc3RydWN0b3Ioc2NvcGU6IENvbnN0cnVjdCwgcHJvcHM6IEFscGhhU3RhZ2VQcm9wcykge1xuICAgICAgICBzdXBlcihzY29wZSwgcHJvcHMuc3RhZ2VJZCwgeyBlbnY6IHtcbiAgICAgICAgICAgIGFjY291bnQ6ICAgIHByb3BzLmFjY291bnQsXG4gICAgICAgICAgICByZWdpb246ICAgICBwcm9wcy5yZWdpb25cbiAgICAgICAgfX0pO1xuXG4gICAgfVxuXG59XG4iXX0=