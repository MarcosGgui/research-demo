export interface IReportData {
    id?: number;
    name?: string;
    esIndex?: string;
    cronInfo?: any;
    displayName?: string;
}

export class ReportData implements IReportData {
    constructor(public id?: number, public name?: string, public esIndex?: string, public cronInfo?: any, public displayName?: string) {}
}
