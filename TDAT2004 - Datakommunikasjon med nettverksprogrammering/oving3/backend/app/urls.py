from django.urls import path
from app.views import CompilerView

urlpatterns = [
    path('', CompilerView.as_view()),
]