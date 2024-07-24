from django.shortcuts import render
from django.http import JsonResponse
from django.views import View
import requests
from bs4 import BeautifulSoup

# Create your views here.

class NewsView(View):
    def get(self, request):
        response = requests.get("https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=%EA%B2%BD%EC%A0%9C%EA%B8%B0%EC%82%AC")
        html = response.text
        soup = BeautifulSoup(html, 'html.parser')
        links = soup.select(".news_tit")

        news_data = []
        for link in links:
            title = link.text
            url = link.attrs['href']
            news_data.append({'title': title, 'url': url})

        return JsonResponse(news_data, safe=False)