"use strict";
/**
 * App entrypoint.
 * @version 0.1.0
 */
Object.defineProperty(exports, "__esModule", { value: true });
const core_1 = require("@aws-cdk/core");
const pipelines_1 = require("@aws-cdk/pipelines");
const pipeline_stack_1 = require("../lib/pipeline-stack");
const alpha_stage_1 = require("../lib/alpha-stage");
const constants_1 = require("../lib/constants");
const app = new core_1.App();
const stages = [
    new alpha_stage_1.AlphaStage(app, {
        account: constants_1.Constants.Account,
        region: constants_1.Constants.Region,
        stageId: constants_1.Constants.Stages.Alpha.Id,
        stageName: constants_1.Constants.Stages.Alpha.Name
    })
];
new pipeline_stack_1.PipelineStack(app, {
    account: constants_1.Constants.Account,
    region: constants_1.Constants.Region,
    stackId: constants_1.Constants.CodePipeline.StackId,
    pipelineId: constants_1.Constants.CodePipeline.Id,
    shellStep: new pipelines_1.ShellStep(constants_1.Constants.CodePipeline.ShellStep.Id, {
        commands: []
    }),
    stages: stages
});
app.synth();
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZGV2LWhvdXItY2RrLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiZGV2LWhvdXItY2RrLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiI7QUFBQTs7O0dBR0c7O0FBRUgsd0NBQW1DO0FBQ25DLGtEQUE4QztBQUM5QywwREFBeUU7QUFDekUsb0RBQStDO0FBQy9DLGdEQUE0QztBQUU1QyxNQUFNLEdBQUcsR0FBRyxJQUFJLFVBQUcsRUFBRSxDQUFDO0FBRXRCLE1BQU0sTUFBTSxHQUFHO0lBQ1gsSUFBSSx3QkFBVSxDQUFDLEdBQUcsRUFBRTtRQUNoQixPQUFPLEVBQUkscUJBQVMsQ0FBQyxPQUFPO1FBQzVCLE1BQU0sRUFBSyxxQkFBUyxDQUFDLE1BQU07UUFDM0IsT0FBTyxFQUFJLHFCQUFTLENBQUMsTUFBTSxDQUFDLEtBQUssQ0FBQyxFQUFFO1FBQ3BDLFNBQVMsRUFBRSxxQkFBUyxDQUFDLE1BQU0sQ0FBQyxLQUFLLENBQUMsSUFBSTtLQUN6QyxDQUFDO0NBQ0wsQ0FBQztBQUVGLElBQUksOEJBQWEsQ0FBQyxHQUFHLEVBQUU7SUFDbkIsT0FBTyxFQUFLLHFCQUFTLENBQUMsT0FBTztJQUM3QixNQUFNLEVBQU0scUJBQVMsQ0FBQyxNQUFNO0lBQzVCLE9BQU8sRUFBSyxxQkFBUyxDQUFDLFlBQVksQ0FBQyxPQUFPO0lBQzFDLFVBQVUsRUFBRSxxQkFBUyxDQUFDLFlBQVksQ0FBQyxFQUFFO0lBQ3JDLFNBQVMsRUFBRyxJQUFJLHFCQUFTLENBQUMscUJBQVMsQ0FBQyxZQUFZLENBQUMsU0FBUyxDQUFDLEVBQUUsRUFBRTtRQUMzRCxRQUFRLEVBQUUsRUFBRTtLQUNmLENBQUM7SUFDRixNQUFNLEVBQU0sTUFBTTtDQUNyQixDQUFDLENBQUM7QUFFSCxHQUFHLENBQUMsS0FBSyxFQUFFLENBQUEiLCJzb3VyY2VzQ29udGVudCI6WyIvKipcbiAqIEFwcCBlbnRyeXBvaW50LlxuICogQHZlcnNpb24gMC4xLjBcbiAqL1xuXG5pbXBvcnQgeyBBcHAgfSBmcm9tICdAYXdzLWNkay9jb3JlJ1xuaW1wb3J0IHsgU2hlbGxTdGVwIH0gZnJvbSAnQGF3cy1jZGsvcGlwZWxpbmVzJ1xuaW1wb3J0IHsgUGlwZWxpbmVTdGFjaywgUGlwZWxpbmVTdGFja1Byb3BzIH0gZnJvbSAnLi4vbGliL3BpcGVsaW5lLXN0YWNrJ1xuaW1wb3J0IHsgQWxwaGFTdGFnZSB9IGZyb20gJy4uL2xpYi9hbHBoYS1zdGFnZSdcbmltcG9ydCB7IENvbnN0YW50cyB9IGZyb20gJy4uL2xpYi9jb25zdGFudHMnXG5cbmNvbnN0IGFwcCA9IG5ldyBBcHAoKTtcblxuY29uc3Qgc3RhZ2VzID0gW1xuICAgIG5ldyBBbHBoYVN0YWdlKGFwcCwge1xuICAgICAgICBhY2NvdW50OiAgIENvbnN0YW50cy5BY2NvdW50LFxuICAgICAgICByZWdpb246ICAgIENvbnN0YW50cy5SZWdpb24sXG4gICAgICAgIHN0YWdlSWQ6ICAgQ29uc3RhbnRzLlN0YWdlcy5BbHBoYS5JZCxcbiAgICAgICAgc3RhZ2VOYW1lOiBDb25zdGFudHMuU3RhZ2VzLkFscGhhLk5hbWVcbiAgICB9KVxuXTtcblxubmV3IFBpcGVsaW5lU3RhY2soYXBwLCB7XG4gICAgYWNjb3VudDogICAgQ29uc3RhbnRzLkFjY291bnQsXG4gICAgcmVnaW9uOiAgICAgQ29uc3RhbnRzLlJlZ2lvbixcbiAgICBzdGFja0lkOiAgICBDb25zdGFudHMuQ29kZVBpcGVsaW5lLlN0YWNrSWQsXG4gICAgcGlwZWxpbmVJZDogQ29uc3RhbnRzLkNvZGVQaXBlbGluZS5JZCxcbiAgICBzaGVsbFN0ZXA6ICBuZXcgU2hlbGxTdGVwKENvbnN0YW50cy5Db2RlUGlwZWxpbmUuU2hlbGxTdGVwLklkLCB7XG4gICAgICAgIGNvbW1hbmRzOiBbXVxuICAgIH0pLFxuICAgIHN0YWdlczogICAgIHN0YWdlc1xufSk7XG5cbmFwcC5zeW50aCgpXG4iXX0=