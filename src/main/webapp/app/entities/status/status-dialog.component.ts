import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Status } from './status.model';
import { StatusPopupService } from './status-popup.service';
import { StatusService } from './status.service';

@Component({
    selector: 'jhi-status-dialog',
    templateUrl: './status-dialog.component.html'
})
export class StatusDialogComponent implements OnInit {

    status: Status;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private statusService: StatusService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.status.id !== undefined) {
            this.subscribeToSaveResponse(
                this.statusService.update(this.status));
        } else {
            this.subscribeToSaveResponse(
                this.statusService.create(this.status));
        }
    }

    private subscribeToSaveResponse(result: Observable<Status>) {
        result.subscribe((res: Status) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Status) {
        this.eventManager.broadcast({ name: 'statusListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-status-popup',
    template: ''
})
export class StatusPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statusPopupService: StatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.statusPopupService
                    .open(StatusDialogComponent as Component, params['id']);
            } else {
                this.statusPopupService
                    .open(StatusDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
