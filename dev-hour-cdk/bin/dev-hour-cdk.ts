#!/usr/bin/env node

/**
 * App entrypoint.
 * @version 0.1.0
 */

import 'source-map-support/register';
import { App } from 'aws-cdk-lib'
import { ShellStep, CodePipelineSource } from 'aws-cdk-lib/pipelines'
import { PipelineStack, PipelineStackProps } from '../lib/pipeline-stack'
import { AlphaStage } from '../lib/alpha-stage'
import { Constants } from '../lib/constants'

const app = new App();

const stages = [
    new AlphaStage(app, {
        account:   Constants.Account,
        region:    Constants.Region,
        stageId:   Constants.Stages.Alpha.Id,
        stageName: Constants.Stages.Alpha.Name
    })
];

new PipelineStack(app, {
    account:    Constants.Account,
    region:     Constants.Region,
    stackId:    Constants.CodePipeline.StackId,
    id:         Constants.CodePipeline.Id,
    shellStep:  new ShellStep(Constants.CodePipeline.ShellStep.Id, {
        input:  CodePipelineSource.connection(Constants.CodeCommit.Repository, Constants.CodeCommit.Branches.Main, {
            connectionArn: Constants.CodeCommit.Connection.Arn,
        }),
        commands: [
            'cd dev-hour-cdk',
            'npm install typescript',
            'npx cdk synth',
            'npx cdk deploy'
        ],
        primaryOutputDirectory: Constants.CodeCommit.PrimaryOutputDirectory
    }),
    stages:     stages
});

app.synth()
