/**
 * Alpha Stage. Defines the resources for the Alpha Stage
 * @version 0.1.0
 */
import { Construct, Stage } from '@aws-cdk/core';
export interface AlphaStageProps {
    account: string;
    region: string;
    stageId: string;
    stageName: string;
}
export declare class AlphaStage extends Stage {
    constructor(scope: Construct, props: AlphaStageProps);
}
