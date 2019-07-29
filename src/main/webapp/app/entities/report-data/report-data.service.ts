import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReportData } from 'app/shared/model/report-data.model';

type EntityResponseType = HttpResponse<IReportData>;
type EntityArrayResponseType = HttpResponse<IReportData[]>;

@Injectable({ providedIn: 'root' })
export class ReportDataService {
    public resourceUrl = SERVER_API_URL + 'api/report-data';

    constructor(protected http: HttpClient) {}

    create(reportData: IReportData): Observable<EntityResponseType> {
        return this.http.post<IReportData>(this.resourceUrl, reportData, { observe: 'response' });
    }

    update(reportData: IReportData): Observable<EntityResponseType> {
        return this.http.put<IReportData>(this.resourceUrl, reportData, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IReportData>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IReportData[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
