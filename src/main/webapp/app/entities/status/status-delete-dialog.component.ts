import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Status } from './status.model';
import { StatusPopupService } from './status-popup.service';
import { StatusService } from './status.service';

@Component({
    selector: 'jhi-status-delete-dialog',
    templateUrl: './status-delete-dialog.component.html'
})
export class StatusDeleteDialogComponent {

    status: Status;

    constructor(
        private statusService: StatusService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.statusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'statusListModification',
                content: 'Deleted an status'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-status-delete-popup',
    template: ''
})
export class StatusDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private statusPopupService: StatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.statusPopupService
                .open(StatusDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
