/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ResearchDemoTestModule } from '../../../test.module';
import { ReportDataUpdateComponent } from 'app/entities/report-data/report-data-update.component';
import { ReportDataService } from 'app/entities/report-data/report-data.service';
import { ReportData } from 'app/shared/model/report-data.model';

describe('Component Tests', () => {
    describe('ReportData Management Update Component', () => {
        let comp: ReportDataUpdateComponent;
        let fixture: ComponentFixture<ReportDataUpdateComponent>;
        let service: ReportDataService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ResearchDemoTestModule],
                declarations: [ReportDataUpdateComponent]
            })
                .overrideTemplate(ReportDataUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ReportDataUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReportDataService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new ReportData(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.reportData = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new ReportData();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.reportData = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
