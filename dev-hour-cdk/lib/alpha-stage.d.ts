/**
 * Alpha Stage
 * @version 0.9.0
 */
import { Construct } from 'constructs';
import { Stage } from 'aws-cdk-lib';
export interface AlphaStageProps {
    account: string;
    region: string;
    stageId: string;
    stageName: string;
}
export declare class AlphaStage extends Stage {
    private readonly cognitoStack;
    private readonly vpcStack;
    constructor(scope: Construct, props: AlphaStageProps);
}
