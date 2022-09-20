"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PipelineStack = void 0;
const aws_cdk_lib_1 = require("aws-cdk-lib");
const pipelines_1 = require("aws-cdk-lib/pipelines");
const constants_1 = require("./constants");
/// --------------------
/// Class Implementation
class PipelineStack extends aws_cdk_lib_1.Stack {
    /// -----------
    /// Constructor
    constructor(scope, props) {
        super(scope, props.stackId, { env: {
                account: props.account,
                region: props.region
            } });
        this.pipeline = new pipelines_1.CodePipeline(this, props.pipelineId, {
            selfMutation: constants_1.Constants.CodePipeline.SelfMutate,
            synth: props.shellStep
        });
        props.stages.forEach((stage) => {
            this.pipeline.addStage(stage);
        });
    }
}
exports.PipelineStack = PipelineStack;
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoicGlwZWxpbmUtc3RhY2suanMiLCJzb3VyY2VSb290IjoiIiwic291cmNlcyI6WyJwaXBlbGluZS1zdGFjay50cyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiOzs7QUFLQSw2Q0FBMEM7QUFDMUMscURBQStEO0FBQy9ELDJDQUF1QztBQWN2Qyx3QkFBd0I7QUFDeEIsd0JBQXdCO0FBRXhCLE1BQWEsYUFBYyxTQUFRLG1CQUFLO0lBT3BDLGVBQWU7SUFDZixlQUFlO0lBRWYsWUFBWSxLQUFnQixFQUFFLEtBQXlCO1FBQ25ELEtBQUssQ0FBQyxLQUFLLEVBQUUsS0FBSyxDQUFDLE9BQU8sRUFBRSxFQUFFLEdBQUcsRUFBRTtnQkFDL0IsT0FBTyxFQUFFLEtBQUssQ0FBQyxPQUFPO2dCQUN0QixNQUFNLEVBQUcsS0FBSyxDQUFDLE1BQU07YUFDeEIsRUFBQyxDQUFDLENBQUM7UUFFSixJQUFJLENBQUMsUUFBUSxHQUFHLElBQUksd0JBQVksQ0FBQyxJQUFJLEVBQUUsS0FBSyxDQUFDLFVBQVUsRUFBRTtZQUNyRCxZQUFZLEVBQUUscUJBQVMsQ0FBQyxZQUFZLENBQUMsVUFBVTtZQUMvQyxLQUFLLEVBQVMsS0FBSyxDQUFDLFNBQVM7U0FDaEMsQ0FBQyxDQUFDO1FBRUgsS0FBSyxDQUFDLE1BQU0sQ0FBQyxPQUFPLENBQUUsQ0FBQyxLQUFLLEVBQUUsRUFBRTtZQUM1QixJQUFJLENBQUMsUUFBUSxDQUFDLFFBQVEsQ0FBQyxLQUFLLENBQUMsQ0FBQztRQUNsQyxDQUFDLENBQUMsQ0FBQztJQUVQLENBQUM7Q0FFSjtBQTNCRCxzQ0EyQkMiLCJzb3VyY2VzQ29udGVudCI6WyIvKipcbiAqIFBpcGVsaW5lIHN0YWNrLiBEZWZpbmVzIHRoZSBwaXBlbGluZSBmb3IgdGhlIENESy5cbiAqIEB2ZXJzaW9uIDAuMS4wXG4gKi9cbmltcG9ydCB7IENvbnN0cnVjdCB9IGZyb20gJ2NvbnN0cnVjdHMnXG5pbXBvcnQgeyBTdGFjaywgU3RhZ2UgfSBmcm9tICdhd3MtY2RrLWxpYidcbmltcG9ydCB7IENvZGVQaXBlbGluZSwgU2hlbGxTdGVwIH0gZnJvbSAnYXdzLWNkay1saWIvcGlwZWxpbmVzJ1xuaW1wb3J0IHsgQ29uc3RhbnRzIH0gZnJvbSAnLi9jb25zdGFudHMnXG5cbi8vLyAtLS0tLS0tLS0tXG4vLy8gUHJvcGVydGllc1xuXG5leHBvcnQgaW50ZXJmYWNlIFBpcGVsaW5lU3RhY2tQcm9wcyB7XG4gICAgYWNjb3VudDogICAgc3RyaW5nLFxuICAgIHJlZ2lvbjogICAgIHN0cmluZyxcbiAgICBzdGFja0lkOiAgICBzdHJpbmcsXG4gICAgcGlwZWxpbmVJZDogc3RyaW5nLFxuICAgIHNoZWxsU3RlcDogIFNoZWxsU3RlcCxcbiAgICBzdGFnZXM6ICAgICBTdGFnZVtdXG59XG5cbi8vLyAtLS0tLS0tLS0tLS0tLS0tLS0tLVxuLy8vIENsYXNzIEltcGxlbWVudGF0aW9uXG5cbmV4cG9ydCBjbGFzcyBQaXBlbGluZVN0YWNrIGV4dGVuZHMgU3RhY2sge1xuXG4gICAgLy8vIC0tLS0tLS0tLS0tLS0tLVxuICAgIC8vLyBQcml2YXRlIE1lbWJlcnNcblxuICAgIHByaXZhdGUgcmVhZG9ubHkgcGlwZWxpbmU6IENvZGVQaXBlbGluZTtcblxuICAgIC8vLyAtLS0tLS0tLS0tLVxuICAgIC8vLyBDb25zdHJ1Y3RvclxuXG4gICAgY29uc3RydWN0b3Ioc2NvcGU6IENvbnN0cnVjdCwgcHJvcHM6IFBpcGVsaW5lU3RhY2tQcm9wcykge1xuICAgICAgICBzdXBlcihzY29wZSwgcHJvcHMuc3RhY2tJZCwgeyBlbnY6IHtcbiAgICAgICAgICAgIGFjY291bnQ6IHByb3BzLmFjY291bnQsXG4gICAgICAgICAgICByZWdpb246ICBwcm9wcy5yZWdpb25cbiAgICAgICAgfX0pO1xuXG4gICAgICAgIHRoaXMucGlwZWxpbmUgPSBuZXcgQ29kZVBpcGVsaW5lKHRoaXMsIHByb3BzLnBpcGVsaW5lSWQsIHtcbiAgICAgICAgICAgIHNlbGZNdXRhdGlvbjogQ29uc3RhbnRzLkNvZGVQaXBlbGluZS5TZWxmTXV0YXRlLFxuICAgICAgICAgICAgc3ludGg6ICAgICAgICBwcm9wcy5zaGVsbFN0ZXBcbiAgICAgICAgfSk7XG5cbiAgICAgICAgcHJvcHMuc3RhZ2VzLmZvckVhY2goIChzdGFnZSkgPT4ge1xuICAgICAgICAgICAgdGhpcy5waXBlbGluZS5hZGRTdGFnZShzdGFnZSk7XG4gICAgICAgIH0pO1xuXG4gICAgfVxuXG59XG4iXX0=